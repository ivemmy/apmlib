package com.apm.webview;

import android.os.Environment;
import android.webkit.JavascriptInterface;

import com.apm.log.ApmLogger;
import com.apm.model.webview.MH5ErrorEvent;
import com.apm.model.webview.MH5ResourceTimingEvent;
import com.apm.model.webview.MWebViewAjaxEvent;
import com.apm.model.webview.MWebViewResourceEvent;
import com.apm.service.WebViewTask;
import com.apm.util.PMCTimer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;


/**
 * Created by Hyman on 2016/6/23.
 */
public class CalledByWebview {

    public String className = "";
    public String requestid = null;

    public CalledByWebview() {

        ApmLogger.info("CalledByWebview Start");
    }

    public CalledByWebview(String cn) {
        this.className = cn;
    }

    @JavascriptInterface
    public void send(String msg) {
        //ApmLogger.info("WebView onClick --------- JS Send Data:" + msg);

        handleOnClick(msg);
    }

    @JavascriptInterface
    public void ajaxsend(String msg) {
        //ApmLogger.info("WebView Ajax ---------- JS Send Data:" + msg);

        handleAjax(msg);
    }

    @JavascriptInterface
    public void ressend(String msg) {
        //ApmLogger.info("WebView Resource -------- JS Send Data : " + msg);

        handleResource(msg);
    }

    @JavascriptInterface
    public void errsend(String msg) {
        //ApmLogger.info("WebView Error ----------- JS Send Data : " + msg);

        handleError(msg);
    }


    /**
     * **************************************************************************
     */


    public void handleOnClick(String msg) {

        ApmLogger.info("====================== come in handleOnClick:");
        ApmLogger.info(msg);

    }

    public void handleAjax(String msg) {

        //writeToSD("resource.txt", msg);
        ApmLogger.info("====================== come in handleAjax:");
        ApmLogger.info(msg);

        try {
            msg = replaceData(msg);
            JSONObject ajaxJson = new JSONObject(msg);
            if (!ajaxJson.isNull("type")) {
                String type = ajaxJson.optString("type", null);
                if ((type != null) && (type.equals("monitor_ajax"))) {
                    JSONObject payload = ajaxJson.getJSONObject("payload");
                    String url = payload.optString("url", "");
                    String domain = payload.optString("domain", "");
                    String uri = payload.optString("uri", "");
                    if (domain.equals("")) {
                        if (!url.equals("")) {
                            domain = url;
                        }
                    }
                    long responseTime = 0l;
                    if (!payload.isNull("events")) {
                        JSONArray eventsArr = payload.getJSONArray("events");
                        if (eventsArr.length() > 0) {
                            for (int i = 0; i < eventsArr.length(); i++) {
                                try {
                                    JSONObject event = eventsArr.getJSONObject(i);
                                    MWebViewAjaxEvent wae = new MWebViewAjaxEvent();
                                    //wae.request_id = StringUtil.getUniqueID();
                                    wae.target_name = this.className;
                                    wae.link_url = url;
                                    wae.domain = domain;
                                    wae.uri = uri;
                                    wae.timestamp = PMCTimer.getPMCTimeMilli();
                                    wae.eve_type = event.optString("eve_type", "");
                                    wae.req_url = event.optString("req_url", "");
                                    wae.req_method = event.optString("req_method", "");
                                    wae.is_asyn = event.optBoolean("is_asyn", false);
                                    wae.req_time = PMCTimer.getPMCTimeMilli(event.optLong("req_time", 0L));
                                    wae.req_size = event.optLong("req_size", 0L);
                                    wae.firstbyte_time = PMCTimer.getPMCTimeMilli(event.optLong("firstbyte_time", 0L));
                                    wae.lastbyte_time = PMCTimer.getPMCTimeMilli(event.optLong("lastbyte_time", 0L));
                                    wae.cb_start_time = PMCTimer.getPMCTimeMilli(event.optLong("cb_start_time", 0L));
                                    wae.cb_end_time = PMCTimer.getPMCTimeMilli(event.optLong("cb_end_time", 0L));
                                    wae.res_time = PMCTimer.getPMCTimeMilli(event.optLong("res_time", 0L));
                                    wae.rep_code = event.optInt("rep_code", 0);
                                    wae.code_text = event.optString("code_text", "");
                                    wae.rep_size = event.optLong("rep_size", 0L);
                                    wae.timeout = event.optLong("timeout", 0L);
                                    wae.is_err = event.optInt("is_err", 0);

                                    responseTime = wae.res_time - wae.req_time;

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    ApmLogger.error("WebView Ajax Parse Events Error : " + ex.getMessage());
                                }
                            }

                            // 放到这个if判断力，所有数据都有了才上传
                            payload.put("type", "ajax");
                            payload.put("responseTime", responseTime);
                            WebViewTask.send(payload);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ApmLogger.error("WebView Parse Error - Ajax : " + ex.getMessage());
        }

    }

    public void handleError(String msg) {

        //writeToSD("resource.txt", msg);
        ApmLogger.info("====================== come in handleError:");
        ApmLogger.info(msg);

        try {
            msg = replaceData(msg);

            JSONObject errJson = new JSONObject(msg);
            if (!errJson.isNull("type")) {
                String type = errJson.optString("type", null);
                if ((type != null) && (type.equals("monitor_error"))) {
                    JSONObject payload = errJson.getJSONObject("payload");
                    String url = payload.optString("url", "");
                    String domain = payload.optString("domain", "");
                    String uri = payload.optString("uri", "");
                    if (domain.equals("")) {
                        if (!url.equals("")) {
                            domain = url;
                        }
                    }

                    if (!payload.isNull("error_list")) {
                        JSONArray errorList = payload.getJSONArray("error_list");
                        if (errorList.length() > 0) {
                            MWebViewResourceEvent mre = new MWebViewResourceEvent();
                            //mre.request_id = StringUtil.getUniqueID();
                            mre.target_name = this.className;
                            mre.timestamp = PMCTimer.getPMCTimeMilli();
                            mre.link_url = url;
                            mre.domain = domain;
                            mre.uri = uri;
                            int index = 0;
                            for (int i = 0; i < errorList.length(); i++) {
                                try {
                                    JSONObject err = errorList.getJSONObject(i);
                                    MH5ErrorEvent mee = new MH5ErrorEvent();
                                    //mee.request_id = StringUtil.getUniqueID();
                                    mee.time = PMCTimer.getPMCTimeMilli(err.optLong("time", 0L));
                                    mee.url = err.optString("url", "");
                                    mee.msg = err.optString("msg", "");
                                    mee.line = err.optLong("line", 0L);
                                    mee.column = err.optLong("column", 0L);
                                    mee.type = err.optString("type", "");
                                    mee.stack = err.optString("stack", "");
                                    mre.error_list.add(mee.toString());

                                    index++;

                                    // 上传数据
                                    payload.remove("navigationTiming");
                                    payload.remove("resourceTiming");
                                    payload.put("type", "jsError");
                                    payload.put("errorType", mee.type);
                                    WebViewTask.send(payload);

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    ApmLogger.error("WebView Parse Error - jsError : " + ex.getMessage());
                                }
                            }

                            if (index > 0) {
                                //System.out.println("error: " + mre.toString());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ApmLogger.error("WebView Error - jsError : " + ex.getMessage());
        }

    }

    public void handleResource(String msg) {

        //writeToSD("resource.txt", msg);
        ApmLogger.info("====================== come in handleResource:");
        ApmLogger.info(msg);

        try {
            msg = replaceData(msg);
            JSONObject resJson = new JSONObject(msg);
            if (!resJson.isNull("type")) {
                String type = resJson.optString("type", null);
                if ((type != null) && (type.equals("monitor_resourceTiming"))) {
                    JSONObject payload = resJson.getJSONObject("payload");
                    String url = payload.optString("url", "");
                    String domain = payload.optString("domain", "");
                    String uri = payload.optString("uri", "");

                    if (domain.equals("")) {
                        return;
                    }

                    Long whiteTime = 0L;  // 白屏时间
                    Long consumeTime = 0L;  // 页面耗时

                    MWebViewResourceEvent mre = new MWebViewResourceEvent();

                    int nav_index = 0;
                    if (!payload.isNull("navigationTiming")) {
                        try {
                            JSONObject nav = payload.getJSONObject("navigationTiming");
                            if (nav.length() > 0) {
                                mre.pageTime = PMCTimer.getPMCTimeMilli(nav.optLong("pageTime", 0L));
                                mre.navigationStart = PMCTimer.getPMCTimeMilli(nav.optLong("navigationStart", 0L));
                                mre.redirectStart = PMCTimer.getPMCTimeMilli(nav.optLong("redirectStart", 0L));
                                mre.redirectEnd = PMCTimer.getPMCTimeMilli(nav.optLong("redirectEnd", 0L));
                                mre.fetchStart = PMCTimer.getPMCTimeMilli(nav.optLong("fetchStart", 0L));
                                mre.domainLookupStart = PMCTimer.getPMCTimeMilli(nav.optLong("domainLookupStart", 0L));
                                mre.domainLookupEnd = PMCTimer.getPMCTimeMilli(nav.optLong("domainLookupEnd", 0L));
                                mre.connectStart = PMCTimer.getPMCTimeMilli(nav.optLong("connectStart", 0L));
                                mre.connectEnd = PMCTimer.getPMCTimeMilli(nav.optLong("connectEnd", 0L));
                                mre.secureConnectionStart = PMCTimer.getPMCTimeMilli(nav.optLong("secureConnectionStart", 0L));
                                mre.requestStart = PMCTimer.getPMCTimeMilli(nav.optLong("requestStart", 0L));
                                mre.responseStart = PMCTimer.getPMCTimeMilli(nav.optLong("responseStart", 0L));
                                mre.responseEnd = PMCTimer.getPMCTimeMilli(nav.optLong("resopnseEnd", 0L));
                                mre.unloadEventStart = PMCTimer.getPMCTimeMilli(nav.optLong("unloadEventStart", 0L));
                                mre.unloadEventEnd = PMCTimer.getPMCTimeMilli(nav.optLong("unloadEventEnd", 0L));
                                mre.domLoading = PMCTimer.getPMCTimeMilli(nav.optLong("domLoading", 0L));
                                mre.domInteractive = PMCTimer.getPMCTimeMilli(nav.optLong("domInteractive", 0L));
                                mre.domContentLoadedEventStart = PMCTimer.getPMCTimeMilli(nav.optLong("domContentLoadedEventStart", 0L));
                                mre.domContentLoadedEventEnd = PMCTimer.getPMCTimeMilli(nav.optLong("domContentLoadedEventEnd", 0L));
                                mre.domComplete = PMCTimer.getPMCTimeMilli(nav.optLong("domComplete", 0L));
                                mre.loadEventStart = PMCTimer.getPMCTimeMilli(nav.optLong("loadEventStart", 0L));
                                mre.loadEventEnd = PMCTimer.getPMCTimeMilli(nav.optLong("loadEventEnd", 0L));
                                if (mre.responseEnd == 0L) {
                                    if (mre.responseStart != 0L) {
                                        if (mre.domLoading > mre.responseStart) {
                                            mre.responseEnd = mre.domLoading;
                                        } else if (mre.domInteractive > mre.responseStart) {
                                            mre.responseEnd = mre.domInteractive;
                                        } else if (mre.domContentLoadedEventStart > mre.responseStart) {
                                            mre.responseEnd = mre.domContentLoadedEventStart;
                                        } else if (mre.domContentLoadedEventEnd > mre.responseStart) {
                                            mre.responseEnd = mre.domContentLoadedEventEnd;
                                        } else if (mre.domComplete > mre.responseStart) {
                                            mre.responseEnd = mre.domComplete;
                                        }
                                    }
                                }
                                if (mre.domContentLoadedEventEnd == 0L) {
                                    mre.domContentLoadedEventEnd = mre.domComplete;
                                }
                                //this.requestid = StringUtil.getUniqueID();

                                whiteTime = mre.domContentLoadedEventEnd - mre.navigationStart;
                                consumeTime = mre.pageTime - mre.navigationStart;

                                nav_index++;
                            } else {
                                nav_index = 0;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            ApmLogger.error("WebView Parse Error - Resource NavigationTiming : " + ex.getMessage());
                            nav_index = 0;
                        }
                    }
                    int res_index = 0;
                    if (!payload.isNull("resourceTiming")) {
                        try {
                            JSONArray resArr = payload.getJSONArray("resourceTiming");
                            if (resArr.length() > 0) {
                                for (int i = 0; i < resArr.length(); i++) {
                                    try {
                                        JSONObject res = resArr.getJSONObject(i);
                                        MH5ResourceTimingEvent me = new MH5ResourceTimingEvent();
                                        me.connectStart = res.optLong("connectStart", 0L);
                                        me.connectEnd = res.optLong("connectEnd", 0L);
                                        me.domainLookupStart = res.optLong("domainLookupStart", 0L);
                                        me.domainLookupEnd = res.optLong("domainLookupEnd", 0L);
                                        me.duration = res.optLong("duration", 0L);
                                        if (me.duration >= 0L) {
                                            me.entryType = res.optString("entryType", "");
                                            me.fetchStart = res.optLong("fetchStart", 0L);
                                            me.initiatorType = res.optString("initiatorType", "");
                                            me.name = res.optString("name", "");
                                            me.redirectStart = res.optLong("redirectStart", 0L);
                                            me.redirectEnd = res.optLong("redirectEnd", 0L);
                                            me.requestStart = res.optLong("resquestStart", 0L);
                                            me.responseStart = res.optLong("responseStart", 0L);
                                            me.responseEnd = res.optLong("responseEnd", 0L);
                                            me.secureConnectionStart = res.optLong("secureConnectionStart", 0L);
                                            me.startTime = res.optLong("startTime", 0L);

                                            mre.resource_time.add(me.toString());
                                            res_index++;
                                        }
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                        ApmLogger.error("WebView Parse Error - Resource ResourceTiming : " + ex.getMessage());
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            res_index = 0;
                        }
                    }
                    if (nav_index > 0) {
                        //mre.request_id = (this.requestid == null ? StringUtil.getUniqueID() : this.requestid);
                        mre.timestamp = PMCTimer.getPMCTimeMilli();
                        mre.target_name = this.className;
                        mre.link_url = url;
                        mre.domain = domain;
                        mre.uri = uri;

                        payload.put("type", "pageLoad");
                        payload.put("whiteTime", whiteTime);
                        payload.put("consumeTime", consumeTime);

                        WebViewTask.send(payload);

                    } else if (res_index > 0) {
                        //mre.request_id = (this.requestid == null ? StringUtil.getUniqueID() : this.requestid);
                        mre.timestamp = PMCTimer.getPMCTimeMilli();
                        mre.target_name = this.className;
                        mre.link_url = url;
                        mre.domain = domain;
                        mre.uri = uri;

                        ApmLogger.info("====================== ignore this one");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String replaceData(String msg) {
        if (msg != null) {
            return msg.replace("\\n", "<br>");
        }
        return "";
    }

    private void writeToSD(String fileName, String s) {
        try {
            String pathName = Environment.getExternalStorageDirectory().getPath() + "/BetterUse/webview";
            //String fileName="json.txt";
            File path = new File(pathName);
            File file = new File(pathName + "/" + fileName);
            if (!path.exists()) {
                path.mkdir();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file, true);
            Writer out = new OutputStreamWriter(stream, "UTF-8");
            //byte[] buf = s.getBytes();
            //stream.write(buf);
            out.write(s);
            out.close();
            stream.close();

            ApmLogger.info("write to sd card success, fileName: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
