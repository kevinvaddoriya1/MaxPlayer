package com.example.videoplayer.fragments;

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
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.VideoAdapter;
import com.example.videoplayer.models.VideoDetails;
import java.util.List;

public class AllVideoFragment extends Fragment {

    private RecyclerView rvVideos;
    private TextView tvTotalVideos;
    private VideoAdapter videoAdapter;
    private List<VideoDetails> videoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvVideos = view.findViewById(R.id.rv_videos);
        tvTotalVideos = view.findViewById(R.id.tv_total_video);

        videoList = BaseActivity.getVideos();

        setupRecyclerView();

        updateTotalVideoCount();
    }

    private void setupRecyclerView() {
        rvVideos.setLayoutManager(new LinearLayoutManager(getContext()));
        videoAdapter = new VideoAdapter(videoList);
        rvVideos.setAdapter(videoAdapter);
    }

    private void updateTotalVideoCount() {
        String totalVideosText = String.format(getString(R.string.totalVideos),String.valueOf(videoList.size()));
        tvTotalVideos.setText(totalVideosText);
    }
}
