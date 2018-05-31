package com.odbpo.fenggou.retrofit2demo.util;

import android.widget.Toast;

import com.odbpo.fenggou.retrofit2demo.MyApplication;

/**
 * @author: zc
 * @Time: 2018/3/2 14:51
 * @Desc:
 */
public class AppToast {

    public static void show(String content) {
        Toast.makeText(MyApplication.context, content, Toast.LENGTH_SHORT).show();
    }

}
