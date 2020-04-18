package com.example.teachingblog.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.teachingblog.utils.VideoFragmentCreator;

public class VideoMainContentAdapter extends FragmentPagerAdapter {

    public VideoMainContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return VideoFragmentCreator.getFragment(position);
    }

    @Override
    public int getCount() {
        return VideoFragmentCreator.PAGE_COUNT;
    }
}
