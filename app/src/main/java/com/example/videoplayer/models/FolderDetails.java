package com.example.videoplayer.models;

public class FolderDetails {
    private String folderName;
    private int videoCount;

    public FolderDetails(String folderName, int videoCount) {
        this.folderName = folderName;
        this.videoCount = videoCount;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }
}
