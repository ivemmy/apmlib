package com.apm.webview;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Hyman on 2016/6/26.
 */
public class APMWebViewClient extends WebViewClient {

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        String injectJs = "http://i-test.com.cn/junchao/webview/collector.js";

        String msg = "javascript:" +
                "   (function() { " +
                "       var script=document.createElement('script');  " +
                "       script.setAttribute('type','text/javascript');  " +
                "       script.setAttribute('src', '" + injectJs + "'); " +
                "       document.head.appendChild(script); " +
                "       script.onload = function() {" +
                "           startWebViewMonitor();" +
                "           pmcAddEvent();" +
                "       }; " +
                "    }" +
                "    )();";

        view.loadUrl(msg);
    }
}
