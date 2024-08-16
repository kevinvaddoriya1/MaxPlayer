package com.example.videoplayer;

import android.app.Application;
import com.example.videoplayer.models.FolderDetails;
import com.example.videoplayer.videoUtils.VideoFetcher;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends Application {

    public static BaseActivity context;
    public static List<FolderDetails> FOLDERLIST = new ArrayList<>();
    private static VideoFetcher videoFetcher;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MMKV.initialize(this);
        videoFetcher = VideoFetcher.getInstance();

//        FOLDERLIST = videoFetcher.fetchAllFolders(this);
    }

    public static BaseActivity getContext(){
        return context;
    }

    public static VideoFetcher getVideoFetcher() {
        return videoFetcher;
    }

//    public static List<FolderDetails> getFolder() {
//        return FOLDERLIST;
//    }

}
