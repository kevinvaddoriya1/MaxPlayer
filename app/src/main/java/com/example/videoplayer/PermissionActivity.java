package com.example.videoplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.tencent.mmkv.MMKV;

import java.util.Arrays;

public class PermissionActivity extends AppCompatActivity {

    private TextView tv_permission_title;
    private TextView tv_permission_description;
    private static final int REQUEST_PERMISSION = 1;
    private static final int REQUEST_PERMISSION_SETTINGS = 10;
    private static final String TAG = "PermissionActivity";
    private Button btn_allow_permission;
    private String[] storagePermissions;


    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 10) {
            this.startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.windowBackground));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        tv_permission_title = findViewById(R.id.tv_permission_title);
        tv_permission_description = findViewById(R.id.tv_permission_description);
        btn_allow_permission = findViewById(R.id.btn_allow_permission);

        tv_permission_title.setText(String.format(getString(R.string.strPermissionTitle),getString(R.string.app_name)));
        tv_permission_description.setText(String.format(getString(R.string.strPermissionDesc2),getString(R.string.app_name)));
        this.storagePermissions = Build.VERSION.SDK_INT >= 33 ? new String[]{"android.permission.READ_MEDIA_VIDEO", "android.permission.POST_NOTIFICATIONS"} : (Build.VERSION.SDK_INT >= 30 ? new String[]{"android.permission.READ_EXTERNAL_STORAGE"} : new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"});
        btn_allow_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission();
            }
        });
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Arrays.toString(this.storagePermissions)) != 0) {
            ActivityCompat.requestPermissions(this, this.storagePermissions, 1);
            return;
        }
        MMKV.defaultMMKV().encode("is_PG", true);
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @SuppressLint("SetTextI18n")
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        super.onRequestPermissionsResult(n, arrstring, arrn);
        if (n == 1) {
            if (arrn.length > 0 && arrn[0] == 0) {
                Log.d((String) TAG, (String) "onRequestPermissionsResult: Permission Granted");
                MMKV.defaultMMKV().encode("is_PG", true);
                this.startActivity(new Intent((Context) this, MainActivity.class));
                this.finish();
                return;
            }
            btn_allow_permission.setText("Goto Setting");
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts((String) "package", (String) PermissionActivity.this.getPackageName(), null));
            PermissionActivity.this.startActivityForResult(intent, 10);
        }
    }
}