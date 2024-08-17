package com.example.videoplayer.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.AdapterItemClickListener;
import com.example.videoplayer.adapters.VideoAdapter;
import com.example.videoplayer.models.VideoDetails;
import com.example.videoplayer.videoUtils.OnEventListener;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class VideoFilesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AdapterItemClickListener<VideoDetails> {

    private String key;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvVideos;
    private VideoAdapter videoAdapter;
    private List<VideoDetails> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_files);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        Bundle bundle = getIntent().getBundleExtra("folderBundle");

        if (bundle != null) {
            key = bundle.getString("key");
            String name = bundle.getString("name");

            toolbar.setTitle(name);

            refreshLayout = findViewById(R.id.refreshLayout);
            refreshLayout.setColorSchemeResources(R.color.colorPrimary);
            refreshLayout.setOnRefreshListener(this);
            rvVideos = findViewById(R.id.rv_videos);

            onRefresh();

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private void showVideos() {
        BaseActivity.getVideoFetcher().getVideos(key, new OnEventListener<List<VideoDetails>>() {
            @Override
            public void onSuccess(List<VideoDetails> data) {
                super.onSuccess(data);
                videoList.clear();
                videoList.addAll(data);

                refreshLayout.setRefreshing(false);
            }
        });

        videoAdapter = new VideoAdapter(videoList,this);
        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        rvVideos.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        showVideos();
    }

    @Override
    public void onClicked(VideoDetails data, int position) {

    }
}