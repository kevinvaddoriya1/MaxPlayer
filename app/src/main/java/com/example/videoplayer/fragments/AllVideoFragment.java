package com.example.videoplayer.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.VideoAdapter;
import com.example.videoplayer.models.VideoDetails;
import com.example.videoplayer.videoUtils.OnEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllVideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvVideos;
    private TextView tvTotalVideos;
    private VideoAdapter videoAdapter;
    private List<VideoDetails> videoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        rvVideos = view.findViewById(R.id.rv_videos);
        tvTotalVideos = view.findViewById(R.id.tv_total_video);

//        onRefresh();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void showVideos() {
        BaseActivity.getVideoFetcher().fetchAllVideos(new OnEventListener<List<VideoDetails>>() {
            @Override
            public void onSuccess(List<VideoDetails> data) {
                super.onSuccess(data);
                videoList.clear();
                videoList.addAll(data);

                refreshLayout.setRefreshing(false);
            }
        });
        videoAdapter = new VideoAdapter(videoList);
        rvVideos.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVideos.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
        updateTotalVideoCount();
    }

    private void updateTotalVideoCount() {
        String totalVideosText = String.format(getString(R.string.totalVideos),String.valueOf(videoList.size()));
        tvTotalVideos.setText(totalVideosText);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);

        showVideos();
    }

    @Override
    public void onResume() {
        super.onResume();
        onRefresh();
    }
}
