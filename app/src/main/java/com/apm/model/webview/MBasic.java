package com.apm.model.webview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyman on 2016/7/9.
 */
public class MBasic extends EventBase {

    public static final String jsonPropName = "basic";
    public String screen;
    public String app_key;
    public String uid;
    public long cpu_used;
    public int device_type;
    public int root;
    public int dump_energy;
    public long useful_space;
    public String cpu_model;
    public int orientation;
    public long mem_used;
    public String os;
    public int os_enum = 2;
    public String branch;
    public String device_name;
    public String device_version;
    public String version;
    public String sdk_v = "1.3.9";
    public boolean is_tracking = false;
    public String operator;
    public int is_new;
    public long current_time;
    public long account_id;
    public String access;
    public String channel;
    public List<String> sessions = new ArrayList();
    public List<String> requests = new ArrayList();
    public List<String> viewController = new ArrayList();
    public List<String> thread_breakdowns = new ArrayList();
    public List<String> startups = new ArrayList();
    public List<String> events = new ArrayList();
    public List<String> interaction = new ArrayList();
    public List<String> fragment = new ArrayList();
    public List<String> socket = new ArrayList();
    public List<String> anr = new ArrayList();
    public List<String> webview_resource = new ArrayList();
    public List<String> webview_ajax = new ArrayList();

    public void addList(MBasic b, String type, String value) {
        if (type.equals("requests")) {
            b.requests.add(value);
        } else if (type.equals("nest_sessions")) {
            b.sessions.add(value);
        } else if (type.equals("thread_breakdowns")) {
            b.thread_breakdowns.add(value);
        } else if (type.equals("nest_view_tracking")) {
            b.viewController.add(value);
        } else if (type.equals("nest_fragment_tracking")) {
            b.fragment.add(value);
        } else if (type.equals("nest_startups")) {
            b.startups.add(value);
        } else if (type.equals("nest_event_tracking")) {
            b.events.add(value);
        } else if (type.equals("t")) {
            b.interaction.add(value);
        } else if (type.equals("nest_socket")) {
            b.socket.add(value);
        } else if (type.equals("nest_anr")) {
            b.anr.add(value);
        } else if (type.equals("nest_resource_tracking")) {
            b.webview_resource.add(value);
        } else if (type.equals("nest_ajax_tracking")) {
            b.webview_ajax.add(value);
        }
    }

    public String toString() {
        return "{" + this.q + "basic" + this.q + ":" + "{" + this.q + "access" + this.q + ":" + this.q + this.access + this.q + "," + this.q + "app_key" + this.q + ":" + this.q + this.app_key + this.q + "," + this.q + "uid" + this.q + ":" + this.q + this.uid + this.q + "," + this.q + "cpu_used" + this.q + ":" + this.cpu_used + "," + this.q + "mem_used" + this.q + ":" + this.mem_used + "," + this.q + "root" + this.q + ":" + this.root + "," + this.q + "orientation" + this.q + ":" + this.orientation + "," + this.q + "device_type" + this.q + ":" + this.device_type + "," + this.q + "dump_energy" + this.q + ":" + this.dump_energy + "," + this.q + "useful_space" + this.q + ":" + this.useful_space + "," + this.q + "cpu_model" + this.q + ":" + this.q + this.cpu_model + this.q + "," + this.q + "os" + this.q + ":" + this.q + this.os + this.q + "," + this.q + "branch" + this.q + ":" + this.q + this.branch + this.q + "," + this.q + "device_name" + this.q + ":" + this.q + this.device_name + this.q + "," + this.q + "device_version" + this.q + ":" + this.q + this.device_version + this.q + "," + this.q + "version" + this.q + ":" + this.q + this.version + this.q + "," + this.q + "sdk_v" + this.q + ":" + this.q + this.sdk_v + this.q + "," + this.q + "operator" + this.q + ":" + this.q + this.operator + this.q + "," + this.q + "screen" + this.q + ":" + this.q + this.screen + this.q + "," + this.q + "is_tracking" + this.q + ":" + this.is_tracking + "," + this.q + "channel" + this.q + ":" + this.q + this.channel + this.q + "," + this.q + "is_new" + this.q + ":" + this.is_new + "," + this.q + "os_enum" + this.q + ":" + this.os_enum + "," + this.q + "current_time" + this.q + ":" + this.current_time + "," + this.q + "account_id" + this.q + ":" + this.account_id + "}," + this.q + "nest_startups" + this.q + ":" + this.startups.toString() + "," + this.q + "nest_sessions" + this.q + ":" + this.sessions.toString() + "," + this.q + "nest_event_tracking" + this.q + ":" + this.events.toString() + "," + this.q + "requests" + this.q + ":" + this.requests.toString() + "," + this.q + "nest_view_tracking" + this.q + ":" + this.viewController.toString() + "," + this.q + "thread_breakdowns" + this.q + ":" + this.thread_breakdowns.toString() + "," + this.q + "t" + this.q + ":" + this.interaction.toString() + "," + this.q + "nest_fragment_tracking" + this.q + ":" + this.fragment.toString() + "," + this.q + "nest_socket" + this.q + ":" + this.socket.toString() + "," + this.q + "nest_anr" + this.q + ":" + this.anr.toString() + "," + this.q + "nest_resource_tracking" + this.q + ":" + this.webview_resource.toString() + "," + this.q + "nest_ajax_tracking" + this.q + ":" + this.webview_ajax.toString() + "}";
    }
}

