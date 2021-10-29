package com.eagle.ERoute;

import android.app.Application;

import com.eagle.router_api.Router;

/**
 * @author linquandong
 * @create 2021/10/28
 * @Describe
 **/
public class EApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Router.init(this);
    }
}
