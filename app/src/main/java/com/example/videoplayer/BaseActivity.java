package com.example.videoplayer;

import android.app.Application;
import com.example.videoplayer.models.VideoDetails;
import com.example.videoplayer.videoUtils.VideoFetcher;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends Application {

    public static BaseActivity context;
    public static List<VideoDetails> VIDEOLIST = new ArrayList<>();
    VideoFetcher videoFetcher;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MMKV.initialize(this);

        videoFetcher = VideoFetcher.getInstance();
        VIDEOLIST = videoFetcher.fetchAllVideos(this);
    }

    public static BaseActivity getContext(){
        return context;
    }

    public static List<VideoDetails> getVideos() {
        return VIDEOLIST;
    }

}
