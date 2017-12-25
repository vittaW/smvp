package com.vitta.smvp;

import android.app.Application;
import android.content.Context;

/**
 * 作者：王文婷 邮箱：WVitta@126.com
 * 创建时间：2017/12/22 11:45
 * 描述：App
 */

public class App extends Application {

    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public App getApp() {
        return context;
    }

    public Context getContext(){
       return context;
    }

}
