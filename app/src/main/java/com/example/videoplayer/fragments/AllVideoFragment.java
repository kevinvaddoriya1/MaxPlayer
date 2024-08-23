package com.example.videoplayer.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.AdapterItemClickListener;
import com.example.videoplayer.adapters.VideoAdapter;
import com.example.videoplayer.models.VideoDetails;
import com.example.videoplayer.videoUtils.OnEventListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllVideoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterItemClickListener<VideoDetails> {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rvVideos;
    private TextView tvTotalVideos;
    private VideoAdapter videoAdapter;
    private ArrayList<VideoDetails> videoList = new ArrayList<>();
    private ImageView btn_short;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_video, container, false);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        rvVideos = view.findViewById(R.id.rv_videos);
        tvTotalVideos = view.findViewById(R.id.tv_total_video);
        btn_short = view.findViewById(R.id.shorting_btn);

        onRefresh();

        btn_short.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSortingDialog();
            }
        });

        return view;
    }

    private void showSortingDialog() {


        LayoutInflater inflater = LayoutInflater.from(getContext());
        View dialogView = inflater.inflate(R.layout.dialog_sort_option, null);

        // Reference to RadioGroup
        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroup_sortBy);


        // Build and display the dialog
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Sort Videos")
                .setView(dialogView)
                .setPositiveButton("OK", (dialog, which) -> {
                    // Handle sorting logic based on selected option
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = dialogView.findViewById(selectedId);

                    if (selectedId != -1) {  // Ensure a button is selected
                        if (selectedId == R.id.radioButton_sizeAsc) {
                            sortVideosBySize(true);  // Small to High
                        } else if (selectedId == R.id.radioButton_sizeDesc) {
                            sortVideosBySize(false); // High to Small
                        } else if (selectedId == R.id.radioButton_nameAsc) {
                            sortVideosByName(true);  // A to Z
                        } else if (selectedId == R.id.radioButton_nameDesc) {
                            sortVideosByName(false); // Z to A
                        } else {

                        }
                    } else {

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sortVideosBySize(boolean ascending) {
        Collections.sort(videoList, new Comparator<VideoDetails>() {
            @Override
            public int compare(VideoDetails video1, VideoDetails video2) {
                if (ascending) {
                    return Long.compare(video1.getSize(), video2.getSize()); // Small to High
                } else {
                    return Long.compare(video2.getSize(), video1.getSize()); // High to Small
                }
            }
        });

        videoAdapter.notifyDataSetChanged();
        updateTotalVideoCount();
    }

    private void sortVideosByName(boolean ascending) {
        Collections.sort(videoList, new Comparator<VideoDetails>() {
            @Override
            public int compare(VideoDetails video1, VideoDetails video2) {
                if (ascending) {
                    return video1.getDisplayName().compareToIgnoreCase(video2.getDisplayName());  // A to Z
                } else {
                    return video2.getDisplayName().compareToIgnoreCase(video1.getDisplayName());  // Z to A
                }
            }
        });

        videoAdapter.notifyDataSetChanged();
        updateTotalVideoCount();
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
        videoAdapter = new VideoAdapter(videoList,AllVideoFragment.this);
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

    @Override
    public void onClicked(VideoDetails data, int position) {
        Intent intent = new Intent(getContext(), PlayerActivity.class);
        intent.putParcelableArrayListExtra("videoList", videoList);
        intent.putExtra("position",position);
        getContext().startActivity(intent);

    }
}
