package com.cqxb.until;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class AsyncHttpClientManager {

	public static AsyncHttpClient client = new AsyncHttpClient();

	static {
		client.setTimeout(5000);
	}

	/**
	 * 网络框架请求JSON
	 */
	public static void get(String URL, ResponseHandlerInterface handlerInterface) {
		client.get(URL, handlerInterface);
	}

	public static void post(String URL,RequestParams params, ResponseHandlerInterface handlerInterface) {
		client.post(URL,params, handlerInterface);
	}
}
