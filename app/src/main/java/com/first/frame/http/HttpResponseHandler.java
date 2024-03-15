package com.first.frame.http;

import android.text.TextUtils;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.first.frame.model.BaseModel;
import com.first.frame.utils.JsonUtils;

public class HttpResponseHandler implements StringRequestListener {
    private ResponseListener listener;
    private String url;
    private Object[] extra;

    public HttpResponseHandler(String url, ResponseListener listener, Object... extra) {
        this.listener = listener;
        this.url = url;
        this.extra = extra;
    }

    @Override
    public void onResponse(String response) {
        if (!url.startsWith(UrlConstants.BASE_URL) && !TextUtils.isEmpty(response) && !response.contains("code")) {
            success(response);
            return;
        }

        BaseModel model = new BaseModel();
        try {
            model = JsonUtils.fromJson(response, BaseModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            model = new BaseModel();
            model.status = String.valueOf(HttpUtils.STATE_JSON_ERROR);
        }
        if (String.valueOf(HttpUtils.STATE_SUCCESS).equals(model.getStatus())) {
            success(response);
        } else {
            fail(model.getStatus(), model.getError(), response);
        }
    }

    @Override
    public void onError(ANError anError) {
        if (anError != null) {
            fail(String.valueOf(anError.getErrorCode()), anError.getErrorDetail(), null);
        } else {
            fail(String.valueOf(HttpUtils.STATE_FAILED), null, null);
        }

    }

    private void success(String result) {
        if (listener != null) {
            listener.onSuccess(url, result, extra);
        }
    }

    private void fail(String errorCode, String error, String result) {
        if (listener != null) {
            listener.onFailure(url, errorCode, error, result, extra);
        }
    }


}
