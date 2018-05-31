package com.odbpo.fenggou.retrofit2demo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.odbpo.fenggou.retrofit2demo.MyApplication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author: zc
 * @Time: 2018/5/31 13:40
 * @Desc:
 */
public class SpUtil {
    public final static String FILE_NAME = "sp_file.config";

    /**
     * 单例模式（线程安全且效率高）
     */
    private static SpUtil instance;

    private SpUtil() {

    }

    //获取ImUtil对象
    public static SpUtil getInstance() {

        if (instance == null) {
            synchronized (SpUtil.class) {
                if (instance == null) {
                    instance = new SpUtil();
                }
            }
        }
        return instance;
    }

    private SharedPreferences sp = MyApplication.context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    public void writeStr(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String readStr(String key) {
        String tempStr = sp.getString(key, "");
        return tempStr;
    }

    public void remove(String... k) {
        SharedPreferences.Editor editor = sp.edit();
        for (String key : k) {
            editor.remove(key);
        }
        editor.commit();
    }

    /**
     * 保存对象
     * @param key
     * @param obj
     */
    public void saveObject(String key,Object obj) {
        FileOutputStream out;
        try {
            out = MyApplication.context.openFileOutput(key,Context.MODE_PRIVATE);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            //System.out.println("write object success!");
            objOut.close();
        } catch (IOException e) {
            //System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    /**
     * 读取对象
     * @param key
     * @return
     */
    public Object readObject(String key) {
        Object temp = null;
        FileInputStream in;
        try {
            in = MyApplication.context.openFileInput(key);
            ObjectInputStream objIn = new ObjectInputStream(in);
            temp = objIn.readObject();
            //System.out.println("read object success!");
            objIn.close();
        } catch (IOException e) {
            //System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }

}
