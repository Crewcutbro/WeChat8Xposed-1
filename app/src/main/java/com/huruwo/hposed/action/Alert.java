package com.huruwo.hposed.action;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.gh0u1l5.wechatmagician.spellbook.interfaces.IActivityHook;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Alert implements IActivityHook {
    @Override
    public void onActivityCreating(@NotNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityResuming(@NotNull Activity activity) {

    }

    @Override
    public void onActivityStarting(@NotNull Activity activity) {
        Toast.makeText(activity, "Hello Wechat2021!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMMActivityOptionsMenuCreated(@NotNull Activity activity, @NotNull Menu menu) {

    }
}

