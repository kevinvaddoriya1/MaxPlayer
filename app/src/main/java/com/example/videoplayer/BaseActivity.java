package com.example.videoplayer;

import android.app.Application;
import android.content.Context;
import com.tencent.mmkv.MMKV;

public class BaseActivity extends Application {

    public static BaseActivity context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MMKV.initialize(this);
    }

    public static BaseActivity getContext(){
        return context;
    }


}
