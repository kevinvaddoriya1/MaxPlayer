package com.example.videoplayer.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.example.videoplayer.fragments.AllVideoFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AllVideoFragment();
            case 1:
                return new AllVideoFragment();
            case 2:
                return new AllVideoFragment();
            default:
                return new AllVideoFragment(); // Default case
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Number of fragments
    }
}
