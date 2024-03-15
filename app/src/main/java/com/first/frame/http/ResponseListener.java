package com.first.frame.http;


public interface ResponseListener {

	void onSuccess(String url, String result, Object... extra);

	void onFailure(String url, String errorCode, String error, String result, Object... extra);

}
