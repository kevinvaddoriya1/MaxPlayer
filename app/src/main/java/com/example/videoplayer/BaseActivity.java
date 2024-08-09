package com.example.videoplayer;

import android.app.Application;
import android.content.Context;

public class BaseActivity extends Application {

    public static BaseActivity context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static BaseActivity getContext(){
        return context;
    }


}
