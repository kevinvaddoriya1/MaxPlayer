package com.example.videoplayer.activities;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.videoplayer.R;
import com.example.videoplayer.utils.VideoFetcher;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    String TAG = "TAG___";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        initViews();

        String text = getString(R.string.app_name);
        SpannableString spannableString = new SpannableString(text);
        int firstSpaceIndex = text.indexOf(" ");
        if (firstSpaceIndex != -1) {
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 0, firstSpaceIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimaryText)), firstSpaceIndex, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv_title.setText(spannableString);


        VideoFetcher videoFetcher = VideoFetcher.getInstance();
        List<String> videoFolders = videoFetcher.fetchVideoFolders(this);
        logFoldersAndVideos(videoFetcher, videoFolders);
    }

    private void initViews() {
        tv_title = findViewById(R.id.tv_title);
    }

    @Override
    public void onClick(View view) {

    }


    private void logFoldersAndVideos(VideoFetcher videoFetcher, List<String> folders) {
        if (folders == null) {
            Log.e(TAG, "Folders list is null.");
            return;
        }

        int totalFolders = folders.size();
        Log.i(TAG, "Total folders found: " + totalFolders);

        for (String folder : folders) {
            if (folder == null || folder.isEmpty()) {
                Log.w(TAG, "Folder name is null or empty.");
                continue;
            }

            List<String> videosInFolder = videoFetcher.fetchVideosFromFolder(this, folder);

            if (videosInFolder == null) {
                Log.e(TAG, "Failed to retrieve videos for folder: " + folder);
                continue;
            }

            int videoCount = videosInFolder.size();
            Log.i(TAG, "Folder: " + folder);
            Log.i(TAG, "Total videos in folder '" + folder + "': " + videoCount);

            for (String video : videosInFolder) {
                if (video != null) {
                    Log.d(TAG, "Video: " + video);
                } else {
                    Log.w(TAG, "Found a null video path.");
                }
            }
        }
    }
}