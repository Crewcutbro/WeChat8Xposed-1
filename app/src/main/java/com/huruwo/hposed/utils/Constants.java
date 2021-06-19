package com.huruwo.hposed.utils;

import android.os.Environment;



public class Constants {

    public  static String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String INTERCEPTORPATH = String.format("%s/hook.dex", rootPath);

}
