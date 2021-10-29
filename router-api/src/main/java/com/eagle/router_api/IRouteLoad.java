package com.eagle.router_api;

import android.app.Activity;

import java.util.Map;

/**
 * @author linquandong
 * @create 2021/10/27
 * @Describe
 **/
public interface IRouteLoad {
    void loadRoute(Map<String,Class<? extends Activity>> routers);
}
