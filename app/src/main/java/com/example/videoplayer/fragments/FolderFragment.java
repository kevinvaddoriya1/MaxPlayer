package com.example.videoplayer.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.FolderAdapter;
import com.example.videoplayer.models.FolderDetails;
import com.example.videoplayer.videoUtils.OnEventListener;

import java.util.ArrayList;
import java.util.List;

public class FolderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView rv_folders;
    private TextView tv_total_folder;
    private final List<FolderDetails> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_folder, container, false);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(this);
        rv_folders = view.findViewById(R.id.rv_folders);
        tv_total_folder = view.findViewById(R.id.tv_total_folder);

        onRefresh();

        return view;
    }


    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);

        list.clear();
        BaseActivity.getVideoFetcher().getFolder(new OnEventListener<List<FolderDetails>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(List<FolderDetails> data) {
                super.onSuccess(data);
                list.addAll(data);

                FolderAdapter adapter = new FolderAdapter(list);
                rv_folders.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                updateTotalFolderCount();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    private void updateTotalFolderCount() {
        String totalVideosText = String.format(getString(R.string.totalFolders), String.valueOf(list.size()));
        tv_total_folder.setText(totalVideosText);
    }

}
