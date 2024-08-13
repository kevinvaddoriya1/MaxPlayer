package com.example.videoplayer.activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.example.videoplayer.R;
import com.example.videoplayer.adapters.OnboardAdapter;
import com.example.videoplayer.utils.Constants;
import com.tencent.mmkv.MMKV;

import static com.example.videoplayer.utils.Constants.slideImages;
import static com.example.videoplayer.utils.Constants.slideTexts;

public class OnboardingActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    OnboardAdapter adapter;
    Button btnNext, btnStart;
    TextView btnSkip;
    LinearLayout indicatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_activity);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        btnStart = findViewById(R.id.btnStart);
        indicatorLayout = findViewById(R.id.indicatorLayout);

        adapter = new OnboardAdapter(Constants.slideImages, Constants.slideTexts, Constants.subtitle);
        viewPager.setAdapter(adapter);

        btnNext.setOnClickListener(v -> {
            if (viewPager.getCurrentItem() + 1 < adapter.getItemCount()) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishIntroduction();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishIntroduction();
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == adapter.getItemCount() - 1) {
                    btnNext.setVisibility(View.GONE);
                    btnSkip.setVisibility(View.GONE);
                    btnStart.setVisibility(View.VISIBLE);
                } else {
                    btnNext.setVisibility(View.VISIBLE);
                    btnSkip.setVisibility(View.VISIBLE);
                    btnStart.setVisibility(View.GONE);
                }
            }
        });

        for (int i = 0; i < adapter.getItemCount(); i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_dot_indicatore));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);

            indicatorLayout.addView(dot);
        }

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < indicatorLayout.getChildCount(); i++) {
                    indicatorLayout.getChildAt(i).setSelected(i == position);
                }
            }
        });
    }

    private void finishIntroduction() {
        MMKV mmkv = MMKV.defaultMMKV();
        boolean isIntroductionCompleted = mmkv.decodeBool(Constants.IS_ONBOARDING_SHOWED, false);
        if (!isIntroductionCompleted) {
            mmkv.encode(Constants.IS_ONBOARDING_SHOWED, true);
        }

        Intent intent = new Intent(OnboardingActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}