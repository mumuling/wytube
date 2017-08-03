package com.android.action;

import org.json.JSONObject;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.action.param.CommReply;
import com.android.action.param.CommRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cqxb.yecall.YETApplication;

public class NetBase {
    private int method;
    private CommRequest request;
    private String mUrl;
    private Listener<JSONObject> responseListener;
    private ErrorListener errorListener;
    private OnResponseListener mListener;
    private OnMyResponseListener mListeners;
    private Class mClaz;

    /**
     * @author Gang
     */
    public void setMethod(int method) {
        this.method = method;
    }

    public interface OnResponseListener {
        public abstract void onResponse(String statusCode, CommReply reply);
    }

    public interface OnMyResponseListener {
        public abstract void onResponse(String jsonObject);
    }

    protected void setReplyClass(Class claz) {
        mClaz = claz;
    }

    protected void setReplyListener(OnResponseListener listener) {
        mListener = listener;
    }

    protected void setMyReplyListener(OnMyResponseListener listener) {
        mListeners = listener;
    }

    protected void setRequest(CommRequest req) {
        request = req;
    }

    protected void setResponseListener(Listener<JSONObject> listener) {
        responseListener = listener;
    }

    protected void setErrorListener(ErrorListener listener) {
        errorListener = listener;
    }

    protected void setUrl(String url) {
        mUrl = url;
    }

    protected String PackageUrl(String url, String session) {
        return url + ";JSESSIONID=" + session;
    }

    public void exec() {
        // 设置回应listener
        setResponseListener(new Response.Listener<JSONObject>() {
            // 收到正确应答
            @Override
            public void onResponse(JSONObject response) {
                Log.e("___", "response data = " + response.toString());
                // 对收到的应答数据进行解析
                CommReply result = (CommReply) JSON.parseObject(response.toString(), mClaz);
                if (mListener != null) {

                    mListener.onResponse(result.getStatusCode(), result);

                }
            }
        });

        setErrorListener(new ErrorListener() {
            // 收到错误应答
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", " error response  " + error.toString() + " " + error.getMessage() + " " + error.getLocalizedMessage());
                if (mListener != null) {
                    mListener.onResponse("999999", null);
                }
            }
        });

        // 构造发送请求数据
        Log.v("", "Request map:" + JSON.toJSON(request).toString());
        Log.v("", "Request URL:" + mUrl);

        RequestQueue queue = Volley.newRequestQueue(YETApplication.getContext());

        // 发送请求
        try {
            Log.e(getClass().toString(), "注册提交的数据" + JSON.toJSON(request).toString());
            queue.add(new JsonObjectRequest(method, mUrl, new JSONObject(JSON.toJSON(request).toString()), responseListener, errorListener));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void execm() {
        // 设置回应listener
        setResponseListener(new Response.Listener<JSONObject>() {
            // 收到正确应答
            @Override
            public void onResponse(JSONObject response) {
                Log.v("", "response data = " + response.toString());
                // 对收到的应答数据进行解析
                if (mListeners != null) {

                    mListeners.onResponse(response.toString());

                }
            }
        });

        setErrorListener(new ErrorListener() {
            // 收到错误应答
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", " error response  " + error.toString() + " " + error.getMessage() + " " + error.getLocalizedMessage());
                if (mListeners != null) {
                    mListeners.onResponse(null);
                }
            }
        });

        // 构造发送请求数据
        Log.v("", "Request map:" + JSON.toJSON(request).toString());
        Log.v("", "Request URL:" + mUrl);

        RequestQueue queue = Volley.newRequestQueue(YETApplication.getContext());

        // 发送请求
        try {
            queue.add(new JsonObjectRequest(method, mUrl, new JSONObject(JSON.toJSON(request).toString()), responseListener, errorListener));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
