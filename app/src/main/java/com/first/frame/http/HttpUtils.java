package com.first.frame.http;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.AnalyticsListener;
import com.first.frame.FrameApplication;
import com.first.frame.R;
import com.first.frame.utils.LogUtils;
import com.first.frame.utils.Sha256HmacUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private final static String TAG = HttpUtils.class.getSimpleName();

    public static final int CONNECT_TIME_OUT = 30;

    public static int STATE_SUCCESS = 0;
    public static int STATE_FAILED = -1;
    public static int STATE_JSON_ERROR = -2;
    public static int STATE_NET_ERROR = -3;
    public static String CONTENT_TYPE_JSON = "application/json;charset=utf-8";


    public static ANRequest post(Context context, String url, JSONObject bodyParameter , ResponseListener responseListener, Object... extra) {
        url = UrlConstants.BASE_URL + url;
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            if (responseListener != null) {
                responseListener.onFailure(url, String.valueOf(STATE_NET_ERROR), FrameApplication.getContext().getString(R.string.error_internet), null);
            }
            return null;
        }
        Map<String, String> head = getHeaders();
        String sign = Sha256HmacUtils.sha256_HMAC(Sha256HmacUtils.getSignToken(head), Sha256HmacUtils.key);
        head.put("sign", sign);
        if (LogUtils.ALLOW_LOG) {
            LogUtils.d(url, bodyParameter);
            LogUtils.d(head);
        }
        ANRequest.PostRequestBuilder builder = AndroidNetworking.post(url)
                .setTag(context)
                .addHeaders(head)
                .setPriority(Priority.MEDIUM)
                .setContentType(CONTENT_TYPE_JSON)
                .addJSONObjectBody(bodyParameter);
        ANRequest request = builder.build();
        request.setAnalyticsListener(new RequestAnalyticsListener());
        request.getAsString(new HttpResponseHandler(url, responseListener, extra));
        return request;
    }
//
    public static Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();
//        headers.put("access-token", LoginUtils.getToken());
//        headers.put("device-type", "2");
//        headers.put("device-id", com.baseframe.utils.DeviceUtils.getDeviceId());
//        headers.put("language", com.baseframe.utils.language.LanguageUtil.getInstance().getLanguageName());
//        headers.put("version", BuildConfig.VERSION_NAME);
//        headers.put("lat", "");
//        headers.put("lng", "");
//        headers.put("timezone", com.baseframe.utils.SPUtils.getInstance().getString(com.baseframe.utils.Constants.SP_KEY.KEY_TIME_ZONE));
//        headers.put("timestamp", Long.toString(System.currentTimeMillis() / 1000));
//        headers.put("nonce-str", com.baseframe.utils.StringUtils.getRandomString(32));
        return headers;
    }

    public static ANRequest get(Context context, String url, HashMap<String, String> queryParameterMap, ResponseListener responseListener, Object... extra) {
        url = UrlConstants.BASE_URL + url;
        if (!NetWorkUtils.isNetworkAvailable(FrameApplication.getContext())) {
            if (responseListener != null) {
                responseListener.onFailure(url, String.valueOf(STATE_NET_ERROR), FrameApplication.getContext().getString(R.string.error_internet), null);
            }
            return null;
        }
        if (LogUtils.ALLOW_LOG) {
            LogUtils.d(url, queryParameterMap);
        }
        ANRequest.GetRequestBuilder builder = AndroidNetworking.get(url)
                .setTag(context)
                .setPriority(Priority.LOW)
                .addHeaders(getHeaders())
                .addQueryParameter(getRequestParams(queryParameterMap));
        ANRequest request = builder.build()
                .setAnalyticsListener(new RequestAnalyticsListener());
        request.getAsString(new HttpResponseHandler(url, responseListener, extra));

        return request;
    }

    public static ANRequest postObj(Context context, String url, Object object, ResponseListener responseListener, Object... extra) {
        url = UrlConstants.BASE_URL + url;
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            if (responseListener != null) {
                responseListener.onFailure(url, String.valueOf(STATE_NET_ERROR), FrameApplication.getContext().getString(R.string.error_internet), null);
            }
            return null;
        }
        ANRequest.PostRequestBuilder builder = AndroidNetworking.post(url)
                .setTag(context)
                .setPriority(Priority.MEDIUM)
                .addHeaders(getHeaders())
                .setContentType(CONTENT_TYPE_JSON)
                .addApplicationJsonBody(object)
                .addQueryParameter(getRequestParams(null));

        ANRequest request = builder.build();
        request.setAnalyticsListener(new RequestAnalyticsListener());
        request.getAsString(new HttpResponseHandler(url, responseListener, extra));

        return request;
    }

    public static ANRequest postJsonObj(Context context, String url, JSONObject object, ResponseListener responseListener, Object... extra) {
        url = UrlConstants.BASE_URL + url;
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            if (responseListener != null) {
                responseListener.onFailure(url, String.valueOf(STATE_NET_ERROR), FrameApplication.getContext().getString(R.string.error_internet), null);
            }
            return null;
        }
        if (LogUtils.ALLOW_LOG) {
           LogUtils.d(url, object);
        }
        ANRequest.PostRequestBuilder builder = AndroidNetworking.post(url)
                .setTag(context)
                .setPriority(Priority.MEDIUM)
                .addHeaders(getHeaders())
                .setContentType(CONTENT_TYPE_JSON)
                .addJSONObjectBody(object)
                .addQueryParameter(getRequestParams(null));

        ANRequest request = builder.build();
        request.setAnalyticsListener(new RequestAnalyticsListener());
        request.getAsString(new HttpResponseHandler(url, responseListener, extra));

        return request;
    }

    public static ANRequest upload(Context context, String url, HashMap<String, File> files, HashMap<String, String> params, ResponseListener responseListener, Object... extra) {
        url = UrlConstants.BASE_URL + url;
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            if (responseListener != null) {
                responseListener.onFailure(url, String.valueOf(STATE_NET_ERROR), FrameApplication.getContext().getString(R.string.error_internet), null);
            }
            return null;
        }

        ANRequest.MultiPartBuilder builder = AndroidNetworking.upload(url)
                .setTag(context)
                .setPriority(Priority.HIGH)
                .addHeaders(getHeaders())
                .addHeaders("Connection", "close")
                .addMultipartFile(files)
                .addMultipartParameter(params)
                .addQueryParameter(getRequestParams(null));

        ANRequest request = builder.build();
        request.setAnalyticsListener(new RequestAnalyticsListener());
        request.getAsString(new HttpResponseHandler(url, responseListener, extra));

        return request;
    }

    public static class RequestAnalyticsListener implements AnalyticsListener {
        @Override
        public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
            Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
            Log.d(TAG, " bytesSent : " + bytesSent);
            Log.d(TAG, " bytesReceived : " + bytesReceived);
            Log.d(TAG, " isFromCache : " + isFromCache);
        }
    }

    public static HashMap<String, String> getRequestParams(HashMap<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put("clientVersion", "1");

        return params;
    }

}
