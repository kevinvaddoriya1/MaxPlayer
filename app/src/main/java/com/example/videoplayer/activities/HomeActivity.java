package com.example.videoplayer.activities;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.VideoAdapter;
import com.example.videoplayer.models.VideoDetails;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private RecyclerView rv_videos;
    private VideoAdapter adapter;
    private List<VideoDetails> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        list = BaseActivity.getVideos();

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

        adapter = new VideoAdapter(list);
        rv_videos.setAdapter(adapter);

    }

    private void initViews() {
        tv_title = findViewById(R.id.tv_title);
        rv_videos = findViewById(R.id.rv_videos);
    }

    @Override
    public void onClick(View view) {

    }
}