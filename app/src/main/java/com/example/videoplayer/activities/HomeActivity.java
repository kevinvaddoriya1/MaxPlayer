package com.example.videoplayer.activities;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.videoplayer.BaseActivity;
import com.example.videoplayer.R;
import com.example.videoplayer.models.VideoDetails;
import com.example.videoplayer.utils.ViewPagerAdapter;
import com.example.videoplayer.videoUtils.OnEventListener;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ViewPager2 viewPager;
    private ImageView homeIcon, folderIcon, settingIcon;
    private LinearLayout homeLayout, folderLayout, settingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        configureSystemUI();

        initViews();

        setCustomTitle();

        setupViewPager();
    }

    private void configureSystemUI() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_title);
        viewPager = findViewById(R.id.viewpagerTab);
        homeIcon = findViewById(R.id.home);
        folderIcon = findViewById(R.id.folder);
        settingIcon = findViewById(R.id.setting);
        homeLayout = findViewById(R.id.homell);
        folderLayout = findViewById(R.id.folderll);
        settingLayout = findViewById(R.id.settingll);

        homeLayout.setOnClickListener(view -> viewPager.postDelayed(() -> viewPager.setCurrentItem(0, true), 100));
        folderLayout.setOnClickListener(view -> viewPager.postDelayed(() -> viewPager.setCurrentItem(1, true), 100));
        settingLayout.setOnClickListener(view -> viewPager.postDelayed(() -> viewPager.setCurrentItem(2, true), 100));

    }

    private void setCustomTitle() {
        String text = getString(R.string.app_name);
        SpannableString spannableString = new SpannableString(text);

        int firstSpaceIndex = text.indexOf(" ");
        if (firstSpaceIndex != -1) {
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 0, firstSpaceIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimaryText)), firstSpaceIndex, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableString.setSpan(new ForegroundColorSpan(getColor(R.color.colorPrimary)), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvTitle.setText(spannableString);
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateTabIcons(position);
            }
        });
    }

    private void updateTabIcons(int position) {
        int selectedColor = getColor(R.color.colorPrimary);
        int defaultColor = getColor(R.color.colorPrimaryText);

        homeIcon.setColorFilter(position == 0 ? selectedColor : defaultColor);
        folderIcon.setColorFilter(position == 1 ? selectedColor : defaultColor);
        settingIcon.setColorFilter(position == 2 ? selectedColor : defaultColor);
    }
}
