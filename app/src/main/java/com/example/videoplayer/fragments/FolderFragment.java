package com.example.videoplayer.fragments;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.FolderAdapter;
import com.example.videoplayer.adapters.VideoAdapter;
import com.example.videoplayer.models.FolderDetails;
import com.example.videoplayer.models.VideoDetails;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FolderFragment extends Fragment {
    private RecyclerView rvVideos;
    private TextView tvTotalVideos;
    private VideoAdapter videoAdapter;
    private List<FolderDetails> videoList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvVideos = view.findViewById(R.id.rv_videos);
        tvTotalVideos = view.findViewById(R.id.tv_total_video);

        videoList = BaseActivity.getFolder();

        setupRecyclerView();

        updateTotalVideoCount();

    }

    private void setupRecyclerView() {
        rvVideos.setLayoutManager(new LinearLayoutManager(getContext()));
//        videoAdapter = new FolderAdapter(videoList);
        rvVideos.setAdapter(videoAdapter);
    }

    private void updateTotalVideoCount() {
        String totalVideosText = String.format(getString(R.string.totalVideos),String.valueOf(videoList.size()));
        tvTotalVideos.setText(totalVideosText);
    }
}
