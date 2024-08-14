package com.example.videoplayer.models;

public class VideoDetails {
    private String displayName;
    private String path;
    private long size;
    private long duration;
    private String modifiedDate;
    private String folderName;
    private String resolution;
    private String dateAdded;

    // Constructor
    public VideoDetails(String displayName, String path, long size, long duration, String modifiedDate,
                        String folderName, String resolution, String dateAdded) {
        this.displayName = displayName;
        this.path = path;
        this.size = size;
        this.duration = duration;
        this.modifiedDate = modifiedDate;
        this.folderName = folderName;
        this.resolution = resolution;
        this.dateAdded = dateAdded;
    }

    // Getters
    public String getDisplayName() {
        return displayName;
    }

    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public long getDuration() {
        return duration;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getResolution() {
        return resolution;
    }

    public String getDateAdded() {
        return dateAdded;
    }
}
