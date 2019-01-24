package com.apm.test.tool;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.apm.model.HttpInfo;
import com.apm.util.BaseInfoUtil;
import com.apm.util.Config;
import com.apm.util.ContextHolder;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;
import com.apm.util.SystemUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by JJY on 2016/3/23.
 */
public class HttpProxy {
    private SystemUtils systemUtils;
    private static Map<String, HttpInfo> map = new ConcurrentHashMap<String, HttpInfo>();

    public static String GET = "GET";
    public static String POST = "POST";
    public static String DELETE = "DELETE";
    public static String PUT = "PUT";

    //==================================================================
    //版本一：
    //==================================================================

    public static void URL_callonStart(HttpURLConnection conn) {
        if (!Config.networkAccess) {
            return;
        } else {
            if (conn == null) {

            }
            HttpInfo httpInfo = BaseInfoUtil.getInstance(ContextHolder.context).getHttpInfo();
            try {
                httpInfo.setRequestStart(System.currentTimeMillis());
                httpInfo.setUri(conn.getURL().toString());
                httpInfo.setType(conn.getRequestMethod());
                // 设置响应码
                httpInfo.setStatus(conn.getResponseCode());
                // 设置请求方法

            } catch (Exception e) {
                httpInfo.setRequestEnd(System.currentTimeMillis());

                httpInfo.setError(e.getMessage());
                httpInfo.setStatus(404);
                SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
            }
            map.put(httpInfo.getUri() + Thread.currentThread().getId(), httpInfo);
        }
    }

    public static void URL_callonFinish(HttpURLConnection conn) {
        if (!Config.networkAccess) {
            return;
        } else {
            if (conn == null) {
            }
            HttpInfo httpInfo = map.remove(conn.getURL().toString() + Thread.currentThread().getId());
            httpInfo.setRequestEnd(System.currentTimeMillis());
            SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
        }
    }

    public static void Volley_callonStart(StringRequest stringRequest) {
        if (!Config.networkAccess) {
            return;
        } else {
            HttpInfo httpInfo = BaseInfoUtil.getInstance(ContextHolder.context).getHttpInfo();
            try {
                httpInfo.setRequestStart(System.currentTimeMillis());
                httpInfo.setUri(stringRequest.getUrl());
                int methodcode = stringRequest.getMethod();
                if (methodcode == 0)
                    httpInfo.setType("GET");
                else if (methodcode == 1)
                    httpInfo.setType("POST");
                else if (methodcode == 2)
                    httpInfo.setType("PUT");
                else if (methodcode == 3)
                    httpInfo.setType("DELETE");
                // 设置响应码
            } catch (Exception e) {
                httpInfo.setRequestEnd(System.currentTimeMillis());
                httpInfo.setStatus(404);
                httpInfo.setError(e.getMessage());
                SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
                return;
            }
            httpInfo.setStatus(200);
            map.put(httpInfo.getUri() + Thread.currentThread().getId(), httpInfo);
        }
    }

    public static void Volley_callonFinish(String url, String response) {
        if (!Config.networkAccess) {
            return;
        } else {
            HttpInfo httpInfo = map.remove(url + Thread.currentThread().getId());
            httpInfo.setRequestEnd(System.currentTimeMillis());
            SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
        }
    }

    public static void Volley_callonFinish(String url, VolleyError error) {
        if (!Config.networkAccess) {
            return;
        } else {
            HttpInfo httpInfo = map.remove(url + Thread.currentThread().getId());
            httpInfo.setRequestEnd(System.currentTimeMillis());
            httpInfo.setStatus(404);
            httpInfo.setError(error.getMessage());
            SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
        }
    }

    public static void Async_callonStart(AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!Config.networkAccess) {
            return;
        } else {
            HttpInfo httpInfo = BaseInfoUtil.getInstance(ContextHolder.context).getHttpInfo();
            try {
                httpInfo.setRequestStart(System.currentTimeMillis());
                httpInfo.setUri(asyncHttpResponseHandler.getRequestURI().toString());
                httpInfo.setType("");
            } catch (Exception e) {
                httpInfo.setRequestEnd(System.currentTimeMillis());
                httpInfo.setStatus(404);
                httpInfo.setError(e.getMessage());
                SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
            }
            map.put(httpInfo.getUri() + Thread.currentThread().getId(), httpInfo);
        }
    }

    public static void Async_callonFinish(AsyncHttpResponseHandler asyncHttpResponseHandler) {
        if (!Config.networkAccess) {
            return;
        } else {
            String url = asyncHttpResponseHandler.getRequestURI().toString();
            HttpInfo httpInfo = map.remove(url + Thread.currentThread().getId());
            httpInfo.setRequestEnd(System.currentTimeMillis());
            httpInfo.setStatus(200);
            SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
        }
    }

    public static void Async_callonFinish(AsyncHttpResponseHandler asyncHttpResponseHandler, int statusCode) {
        if (!Config.networkAccess) {
            return;
        } else {
            String url = asyncHttpResponseHandler.getRequestURI().toString();
            HttpInfo httpInfo = map.remove(url + Thread.currentThread().getId());
            httpInfo.setRequestEnd(System.currentTimeMillis());
            httpInfo.setStatus(statusCode);
            SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
        }
    }


    //==================================================================s
    //版本二：
    //==================================================================

    public static void httpCallOnStart(String url, String method) {
        System.out.println("Config.networkAccess===" + Config.networkAccess);
        if (!Config.networkAccess) {
            return;
        }
        HttpInfo httpInfo = BaseInfoUtil.getInstance(ContextHolder.context).getHttpInfo();
        httpInfo.setRequestStart(System.currentTimeMillis());
        httpInfo.setUri(url);
        httpInfo.setType(method);
        map.put(httpInfo.getUri() + Thread.currentThread().getId(), httpInfo);
    }

    public static void httpCallOnStart(URL url, String method) {
        httpCallOnStart(url.toString(), method);
    }

    public static void httpCallOnFinish(String url, int code) {
        if (!Config.networkAccess) {
            return;
        }
        HttpInfo httpInfo = map.remove(url + Thread.currentThread().getId());
        try {
            httpInfo.setRequestEnd(System.currentTimeMillis());
            httpInfo.setStatus(code);
        } catch (Exception e) {
            httpInfo.setError(e.getMessage());
            httpInfo.setStatus(404);
        }
        SendDataUtil.SendData(Converter.getHttpInfo(httpInfo), 5);
    }

    public static void httpCallOnFinish(URL url, int code) {
        httpCallOnFinish(url.toString(), code);
    }
}
