package com.huruwo.hposed;

import com.gh0u1l5.wechatmagician.spellbook.SpellBook;
import com.huruwo.hposed.action.Alert;
import com.huruwo.hposed.action.Message;
import com.huruwo.hposed.utils.LogXUtils;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class MainHookLoader implements IXposedHookLoadPackage {

    public static String MAIN_APP = "com.huruwo.hposed";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        LogXUtils.e(loadPackageParam.packageName);
        if(SpellBook.INSTANCE.isImportantWechatProcess(loadPackageParam)){
            LogXUtils.e("hook wechat" +loadPackageParam.packageName);
            List<Object> list = new ArrayList<>();
            list.add(new Alert());
            list.add(new Message());
            SpellBook.INSTANCE.startup(loadPackageParam,list);
        }
    }

}
