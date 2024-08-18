package com.example.videoplayer.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.VideoAdapter;
import com.example.videoplayer.models.VideoDetails;
import com.example.videoplayer.videoUtils.OnEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView rvVideos;
    private VideoAdapter videoAdapter;
    private EditText editText;
    private ImageView closeBtn,back_btn;
    private TextView tvNoVideos;  // Reference to "No Videos" TextView
    private ArrayList<VideoDetails> videoList = new ArrayList<>();
    private ArrayList<VideoDetails> filteredList = new ArrayList<>(); // For filtered data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        rvVideos = findViewById(R.id.rv_videos_search);
        editText = findViewById(R.id.editText);
        closeBtn = findViewById(R.id.close_btn);
        tvNoVideos = findViewById(R.id.tv_no_videos);
        back_btn = findViewById(R.id.back_arrow);// Initialize No Videos TextView

        // Initialize RecyclerView
        rvVideos.setLayoutManager(new LinearLayoutManager(this));
        videoAdapter = new VideoAdapter(videoList, null); // Pass the appropriate listener if needed
        rvVideos.setAdapter(videoAdapter);

        editText.requestFocus();
        // Initially hide the close button and "No Videos" message
        closeBtn.setVisibility(View.INVISIBLE);
        tvNoVideos.setVisibility(View.GONE); // Hide "No Videos" message by default
        BaseActivity.getVideoFetcher().fetchAllVideos(new OnEventListener<List<VideoDetails>>() {
            @Override
            public void onSuccess(List<VideoDetails> data) {
                super.onSuccess(data);
                videoList.clear();
                videoList.addAll(data);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
        // Listen for text changes in the EditText
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Show the close button if there's text, hide it otherwise
                if (charSequence.length() > 0) {
                    closeBtn.setVisibility(View.VISIBLE);
                    filterVideos(charSequence.toString()); // Perform search
                } else {
                    closeBtn.setVisibility(View.INVISIBLE);
                    resetSearch(); // Reset the search and show all videos
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // Set up close button functionality
        closeBtn.setOnClickListener(view -> {
            editText.setText(""); // Clear the search input
            resetSearch(); // Reset the search and show all videos
        });

        // Fetch and populate the video list (replace this with your actual data fetching logic)
        fetchVideos();
    }

    // Filter videos based on the search text
    private void filterVideos(String query) {
        filteredList.clear();
        for (VideoDetails video : videoList) {
            if (video.getDisplayName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(video);
            }
        }

        // Check if there are any results after filtering
        if (filteredList.isEmpty()) {
            rvVideos.setVisibility(View.GONE);  // Hide RecyclerView
            tvNoVideos.setVisibility(View.VISIBLE);  // Show No Videos message
        } else {
            rvVideos.setVisibility(View.VISIBLE);  // Show RecyclerView
            tvNoVideos.setVisibility(View.GONE);  // Hide No Videos message
        }

        // Update the adapter with the filtered list
        videoAdapter.updateData(filteredList);
    }

    // Reset search and show all videos
    private void resetSearch() {
        videoAdapter.updateData(videoList); // Restore the original video list
        // Check if the video list is empty
        if (videoList.isEmpty()) {
            rvVideos.setVisibility(View.GONE);  // Hide RecyclerView
            tvNoVideos.setVisibility(View.VISIBLE);  // Show No Videos message
        } else {
            rvVideos.setVisibility(View.VISIBLE);  // Show RecyclerView
            tvNoVideos.setVisibility(View.GONE);  // Hide No Videos message
        }
    }

    // Fetch videos and populate the video list (replace this with your actual implementation)
    private void fetchVideos() {
    }
}
