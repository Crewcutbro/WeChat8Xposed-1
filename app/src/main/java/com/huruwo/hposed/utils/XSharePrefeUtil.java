package com.huruwo.hposed.utils;

import android.app.AndroidAppHelper;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;


/**
 * @ClassName: XSharePrefeUtil
 *  *
 * @Description: 文件类型
 * @Author: huruwo
 * @Date: 2019/4/16 23:39
 */
public class XSharePrefeUtil {


    public static String getDeviceId() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AndroidAppHelper.currentApplication().getApplicationContext());
        return prefs.getString("DeviceId", DeviceUtils.getAndroidID());
    }

    public static void setDeviceId(String deviceId) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AndroidAppHelper.currentApplication().getApplicationContext());
        prefs.edit().putString("DeviceId", deviceId).apply();
    }

    public static String getDataHost() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AndroidAppHelper.currentApplication().getApplicationContext());
        return prefs.getString("DataHost", "82.156.27.58");
    }

    public static void setDataHost(String host) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AndroidAppHelper.currentApplication().getApplicationContext());
        prefs.edit().putString("DataHost", host).apply();
    }

}

