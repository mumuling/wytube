/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.os.SystemClock;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.action.param.NetParam;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class StringRequest extends Request<String> {
    
    private final int TIMEOUT_MS = 30000;
    private final int MAX_RETRIES = 1;
    private final float BACKOFF_MULT = 1.0f;
    
    private final Listener<String> mListener;

	// 时间测试参数
    private static final String TAG = "StringRequest";
    private final static boolean TEST_PERFORM  = false;
    private static long testTimeStart = 0L;
    private static long testTimeTotalBCall = 0L;
    private static long testTimeTotalACall = 0L;
    private static int testRunTimes = 0;
    
    // 测试时间开始
    private static void testRunStart(String notes) {
        testRunTimes++;
        Log.v(TAG, String.format("%s:start, cn:%d", notes, testRunTimes));
        testTimeStart = SystemClock.elapsedRealtime();
    }
    // 测试时间截取
    private static void testRunCatch(String notes) {
        if( testTimeStart > 0 ) {
            long len = (SystemClock.elapsedRealtime() - testTimeStart);
            testTimeTotalBCall += len;
            Log.v(TAG, String.format("%s:catch, cn:%d, ct:%d, tt-bc:%d", notes, testRunTimes, len, testTimeTotalBCall));
        }
    }
    // 测试时间结束
    private static void testRunStop(String notes) {
        if( testTimeStart > 0 ) {
            long len = (SystemClock.elapsedRealtime() - testTimeStart);
            testTimeTotalACall += len;
            Log.v(TAG, String.format("%s:stop,  cn:%d, ct:%d, tt-ac:%d", notes, testRunTimes, len, testTimeTotalACall));
            testTimeStart = 0L;
        }
    }
    // 打印运行时间（仅供自测用）
    public static void testPrintRunTime(String notes) {
        Log.i(TAG, String.format("%s - Perform Result: total-count:%d, total-bctime:%d, total-actime:%d",
                notes, testRunTimes, testTimeTotalBCall, testTimeTotalACall));
        testTimeTotalBCall = 0L;
        testTimeTotalACall = 0L;
        testRunTimes = 0;
    }
    
	
    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(int method, String url, Listener<String> listener,
            ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequest(String url, Listener<String> listener, ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    /**
     * Creates a new POST request.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     * @param map string map, or null to ignore errors
     */
    public StringRequest(int method, String url, Listener<String> listener,
                ErrorListener errorListener, Map<String, String> map) {
        super(method, url, errorListener, map);
        if( TEST_PERFORM ) { testRunStart("StringRequest"); }
        mListener = listener;
    }

    @Override
    protected void deliverResponse(String response) {
        if( TEST_PERFORM ) { testRunCatch("StringRequest"); }
        mListener.onResponse(response);
        if( TEST_PERFORM ) { testRunStop("StringRequest"); }
    }

    /* (non-Javadoc)
	 * 设置 HTTP 头部字段
     * @see com.android.volley.Request#getHeaders()
     */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        // TODO Auto-generated method stub
        Map<String,String> params = new HashMap<String, String>();
        params.put("Connection", NetParam.HTTP_CONN_CLOSE);
        //params.put("User-Agent", NetParam.USER_AGENT);
        
        params.put("Accept", NetParam.ACCEPT);
        params.put("Content-Type", NetParam.CONTENT_TYPE);
        params.put("Authorization", NetParam.getAuthorization());
        return params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        RetryPolicy retryPolicy = new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT);
        return retryPolicy;
    }
}
