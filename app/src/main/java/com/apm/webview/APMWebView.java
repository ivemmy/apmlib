package com.apm.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by Hyman on 2016/6/23.
 */
public class APMWebView extends WebView {

    public APMWebView(Context context) {
        super(context);
        /*if (Build.VERSION.SDK_INT >= 17) {
            super.addJavascriptInterface(new CalledByWebview17(), "jsinterface");
        } else {
            super.addJavascriptInterface(new CalledByWebview(), "jsinterface");
        }*/
        super.addJavascriptInterface(new CalledByWebview(), "jsinterface");
    }

    public APMWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
       /* if (Build.VERSION.SDK_INT >= 17) {
            super.addJavascriptInterface(new CalledByWebview17(), "jsinterface");
        } else {
            super.addJavascriptInterface(new CalledByWebview(), "jsinterface");
        }*/
        super.addJavascriptInterface(new CalledByWebview(), "jsinterface");
    }

    public APMWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /*if (Build.VERSION.SDK_INT >= 17) {
            super.addJavascriptInterface(new CalledByWebview17(), "jsinterface");
        } else {
            super.addJavascriptInterface(new CalledByWebview(), "jsinterface");
        }*/
        super.addJavascriptInterface(new CalledByWebview(), "jsinterface");
    }

}
