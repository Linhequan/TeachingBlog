package com.example.teachingblog.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.teachingblog.utils.ArticleFragmentCreator;

public class ArticleMainContentAdapter extends FragmentPagerAdapter {

    public ArticleMainContentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ArticleFragmentCreator.getFragment(position);
    }

    @Override
    public int getCount() {
        return ArticleFragmentCreator.PAGE_COUNT;
    }
}
