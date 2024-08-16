package com.example.videoplayer.videoUtils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.models.FolderDetails;
import com.example.videoplayer.models.VideoDetails;

import java.text.SimpleDateFormat;
import java.util.*;

public class VideoFetcher {
    // Singleton instance
    private static VideoFetcher instance;

    public VideoFetcher() {

    }

    public static synchronized VideoFetcher getInstance() {
        if (instance == null) {
            instance = new VideoFetcher();
        }
        return instance;
    }

    // Fetch all videos with details
    public void fetchAllVideos(OnEventListener<List<VideoDetails>> listener) {
        List<VideoDetails> videoDetailsList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_MODIFIED,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.DATE_ADDED
        };

        String sortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC";

        Cursor cursor = BaseActivity.getContext().getContentResolver().query(uri, projection, null, null, sortOrder);
        if (cursor != null) {
            int displayNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
            int durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
            int dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED);
            int folderNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
            int widthColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH);
            int heightColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT);
            int dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while (cursor.moveToNext()) {
                String displayName = cursor.getString(displayNameColumn);
                String path = cursor.getString(dataColumn);
                long size = cursor.getLong(sizeColumn);
                long duration = cursor.getLong(durationColumn);
                String modifiedDate = sdf.format(new Date(cursor.getLong(dateModifiedColumn) * 1000L));
                String folderName = cursor.getString(folderNameColumn);
                String resolution = cursor.getInt(widthColumn) + "x" + cursor.getInt(heightColumn);
                String dateAdded = sdf.format(new Date(cursor.getLong(dateAddedColumn) * 1000L));

                VideoDetails videoDetails = new VideoDetails(displayName, path, size, duration, modifiedDate, folderName, resolution, dateAdded);
                videoDetailsList.add(videoDetails);
            }
            cursor.close();
        }
        listener.onSuccess(videoDetailsList);
    }


    public List<FolderDetails> fetchAllFolders(Context context) {
        List<FolderDetails> folderDetailsList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media._ID
        };

        String sortOrder = MediaStore.Video.Media.BUCKET_DISPLAY_NAME + " ASC";

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);
        if (cursor != null) {
            Map<String, Integer> folderMap = new HashMap<>();

            int folderNameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);

            while (cursor.moveToNext()) {
                String folderName = cursor.getString(folderNameColumn);

                // Increment video count for the folder
                folderMap.put(folderName, folderMap.getOrDefault(folderName, 0) + 1);
            }
            cursor.close();

            // Create FolderDetails objects and populate the list
            for (Map.Entry<String, Integer> entry : folderMap.entrySet()) {
                folderDetailsList.add(new FolderDetails(entry.getKey(), entry.getValue()));
            }
        }
        return folderDetailsList;
    }
}
