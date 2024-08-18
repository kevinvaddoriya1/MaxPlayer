package com.example.videoplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

public class PreferenceUtils {
    final Context mContext;
    final SharedPreferences mSharedPreferences;

    public PreferenceUtils(Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        frameRateMatching = Utilities.isTvBox(context) && Build.VERSION.SDK_INT < 30;
//        loadSavedPreferences();
//        loadPositions();
    }
}
