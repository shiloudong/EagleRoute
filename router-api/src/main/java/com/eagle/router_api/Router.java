package com.eagle.router_api;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.eagle.router_api.utils.ClassUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author linquandong
 * @create 2021/10/27
 * @Describe
 **/
public class Router {
    private static volatile Router mInstance;
    private static Map<String,Class<? extends Activity>> routers = new HashMap<>();
    public static Router getInstance(){
        if(mInstance == null){
            synchronized (Router.class){
                if(mInstance == null){
                    mInstance = new Router();
                }
            }
        }
        return mInstance;
    }
    private static void  loadRouterMap(){

    }
    private static boolean registerRouter;

    public static void init(Context context){
        try {
            loadRouterMap();
            if(registerRouter){
                return;
            }
            Set<String > classNames = ClassUtils.getFileNameByPackageName(context,"com.eagle");
            for (String name : classNames){
                Class clazz = Class.forName(name);
                //判断是否为IRouteload的实现子类
                if(IRouteLoad.class.isAssignableFrom(clazz)){
                    IRouteLoad routeLoad = (IRouteLoad) clazz.newInstance();
                    routeLoad.loadRoute(routers);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(String path,Class<? extends  Activity> cls){
        routers.put(path,cls);
    }

    public void startActivity(Activity activity,String path){
        Class<? extends Activity> cls = routers.get(path);
        if(cls != null){
            Intent intent = new Intent(activity,cls);
            activity.startActivity(intent);
        }
    }
}
