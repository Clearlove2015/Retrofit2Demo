package com.odbpo.fenggou.retrofit2demo;

import android.app.Application;
import android.content.Context;

/**
 * @author: zjl
 * @Time: 2018/5/31 13:41
 * @Desc:
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
