package com.example.videoplayer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tencent.mmkv.MMKV;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    private String[] storagePermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MMKV mMKV = MMKV.defaultMMKV();
        this.storagePermissions = Build.VERSION.SDK_INT >= 33 ? new String[]{"android.permission.READ_MEDIA_VIDEO"} : (Build.VERSION.SDK_INT >= 30 ? new String[]{"android.permission.READ_EXTERNAL_STORAGE"} : new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"});
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.windowBackground));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!MainActivity.this.checkPermissions()) {
                    MainActivity.this.startActivity(new Intent((Context)MainActivity.this, HomeActivity.class));
                    MainActivity.this.finish();
                    return;
                }else {
                    MainActivity.this.startActivity(new Intent((Context)MainActivity.this, PermissionActivity.class));
                    MainActivity.this.finish();
                    return;
                }
            }
        },2000);

    }
    private boolean checkPermissions() {
        String[] arrstring = this.storagePermissions;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (ContextCompat.checkSelfPermission((Context)this, (String)arrstring[i]) == 0) return true;
        }
        return false;
    }
}