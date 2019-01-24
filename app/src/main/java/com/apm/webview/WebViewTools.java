package com.apm.webview;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Hyman on 2016/9/3.
 */
public class WebViewTools {

    /**
     * 当前的WebView对象
     *
     * @param webview
     */
    public static void setUpWithWebView(WebView webview) {
        if (webview == null) {
            return;
        }
        WebSettings setting = webview.getSettings();
        setting.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new CalledByWebview(), "jsinterface");
        webview.setWebViewClient(new APMWebViewClient());
    }

    /**
     * @param webview                                          当前的WebView对象
     * @param webviewClient，如果开发者实现了自己的WebViewClient，那么请传入这个实例
     */
    public static void setUpWithWebView(WebView webview, WebViewClient webviewClient) {
        if (webview == null) {
            return;
        }
        WebSettings setting = webview.getSettings();
        setting.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new CalledByWebview(), "jsinterface");
        if (webviewClient == null) {
            webview.setWebViewClient(new APMWebViewClient());
        } else {
            WebViewClientWrapper webViewClientWrapper = new WebViewClientWrapper(webviewClient);
            webview.setWebViewClient(webViewClientWrapper);
        }
    }
}
