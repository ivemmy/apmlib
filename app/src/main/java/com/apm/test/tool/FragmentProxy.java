package com.apm.test.tool;


import com.apm.model.FragmentInfo;
import com.apm.service.collect.FragmentCollector;
import com.apm.util.BaseInfoUtil;
import com.apm.util.Config;
import com.apm.util.ContextHolder;
import com.apm.util.Converter;
import com.apm.util.SendDataUtil;
import com.apm.util.SystemUtils;

import java.util.HashMap;

/**
 * Created by JJY on 2016/3/22.
 */
public class FragmentProxy {
    private SystemUtils systemUtils;
    static protected HashMap<String, Boolean> APMFragMap = new HashMap<String, Boolean>();

    public static void callonCreate(android.app.Fragment fromfragment) {
        if (!Config.activityColection)
            return;
        else {
            if (fromfragment == null) {
                throw new IllegalArgumentException("fragment is null");
            }
            APMFragMap.put(fromfragment.getClass().getName(), true);
            FragmentInfo fragmentInfo = BaseInfoUtil.getInstance(ContextHolder.context).getFragmentInfo();
            android.app.Fragment fragment = fromfragment;
            fragmentInfo.setName(fragment.getClass().getName());
            // 设置Fragment所在的Activity所在的名字
            fragmentInfo.setWraper(fragment.getActivity().getClass().getName());
            fragmentInfo.setBeforeCreate(System.currentTimeMillis());
            FragmentCollector.setCurrent(fragmentInfo);
        }
    }

    public static void callonCreate(android.support.v4.app.Fragment fromfragment) {
        if (!Config.activityColection)
            return;
        else {
            if (fromfragment == null) {
            }
            APMFragMap.put(fromfragment.getClass().getName(), true);
            FragmentInfo fragmentInfo = BaseInfoUtil.getInstance(ContextHolder.context).getFragmentInfo();
            android.support.v4.app.Fragment fragment = fromfragment;
            fragmentInfo.setName(fragment.getClass().getName());
            // 设置Fragment所在的Activity所在的名字
            fragmentInfo.setWraper(fragment.getActivity().getClass().getName());
            fragmentInfo.setBeforeCreate(System.currentTimeMillis());
            FragmentCollector.setCurrent(fragmentInfo);
        }
    }

    public static void callonResume(android.app.Fragment fromfragment) {
        System.out.println("fragment " + fromfragment.getClass().getName() + " call on resume");
        if (!Config.activityColection)
            return;
        else {
            if (APMFragMap.get(fromfragment.getClass().getName()) == true) {
                FragmentInfo fragmentInfo = FragmentCollector.getCurrent();
                fragmentInfo.setAfterCreate(System.currentTimeMillis());
                SendDataUtil.SendData(Converter.getJson(fragmentInfo), 2);
            }
        }
    }

    public static void callonResume(android.support.v4.app.Fragment fromfragment) {
        if (!Config.activityColection)
            return;
        else {
            if (APMFragMap.get(fromfragment.getClass().getName()) == true) {
                FragmentInfo fragmentInfo = FragmentCollector.getCurrent();
                fragmentInfo.setAfterCreate(System.currentTimeMillis());
                SendDataUtil.SendData(Converter.getJson(fragmentInfo), 2);
            }
        }
    }

    public static void callonPause(android.app.Fragment fromfragment) {
        if (!Config.activityColection)
            return;
        else
            APMFragMap.put(fromfragment.getClass().getName(), false);
    }

    public static void callonPause(android.support.v4.app.Fragment fromfragment) {
        if (!Config.activityColection)
            return;
        else
            APMFragMap.put(fromfragment.getClass().getName(), false);
    }
}
