package com.example.videoplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

public class VideoFetcher {
    // Singleton instance
    private static VideoFetcher instance;

    private VideoFetcher() {
        // Private constructor to prevent instantiation
    }

    public static synchronized VideoFetcher getInstance() {
        if (instance == null) {
            instance = new VideoFetcher();
        }
        return instance;
    }

    // Fetch all video folders
    public List<String> fetchVideoFolders(Context context) {
        List<String> folders = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.Media.BUCKET_DISPLAY_NAME};
        String sortOrder = MediaStore.Video.Media.BUCKET_DISPLAY_NAME + " ASC";

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);
        if (cursor != null) {
            int folderColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                String folder = cursor.getString(folderColumn);
                if (!folders.contains(folder)) {
                    folders.add(folder);
                }
            }
            cursor.close();
        }
        return folders;
    }

    // Fetch videos from a particular folder
    public List<String> fetchVideosFromFolder(Context context, String folderName) {
        List<String> videoPaths = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.Media.DATA};
        String selection = MediaStore.Video.Media.BUCKET_DISPLAY_NAME + "=?";
        String[] selectionArgs = {folderName};
        String sortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC";

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        if (cursor != null) {
            int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            while (cursor.moveToNext()) {
                String videoPath = cursor.getString(dataColumn);
                videoPaths.add(videoPath);
            }
            cursor.close();
        }
        return videoPaths;
    }
}