package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyLog;
import com.android.action.param.NetParam;

public class StringPost extends Request<String>{

    /* 重试机制参数 */
    private final int TIMEOUT_MS = 10000;
    private final int MAX_RETRIES = 1;
    private final float BACKOFF_MULT = 1.0f;
    
    /* http请求编码方式 */
    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final boolean USE_GZIP = false;
    
    private final Listener<String> mListener;
    private String mContent;
    

    public StringPost(String url, String content, Listener<String> listener, ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mContent = content;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // TODO Auto-generated method stub
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        // TODO Auto-generated method stub
        mListener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        // TODO Auto-generated method stub
        //return super.getBody();
        try {
            return mContent == null ? null : mContent.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding %s while trying to get the bytes from String", PROTOCOL_CHARSET);
            return null;
        }
    }
    
    /* 设置 HTTP 头部字段 */
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        // TODO Auto-generated method stub
        Map<String,String> params = new HashMap<String, String>();
        params.put("Connection", NetParam.HTTP_CONN_CLOSE);
//        params.put("User-Agent", NetParam.USER_AGENT);
        
        params.put("Accept", NetParam.ACCEPT);
        params.put("Content-Type", NetParam.CONTENT_TYPE);
        params.put("Authorization", NetParam.getAuthorization());
        
        return params;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // TODO Auto-generated method stub
        RetryPolicy retryPolicy = new DefaultRetryPolicy(TIMEOUT_MS, MAX_RETRIES, BACKOFF_MULT);
        return retryPolicy;
    }
}
