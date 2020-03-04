package com.example.teachingblog.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachingblog.R;
import com.example.teachingblog.base.BaseFragment;

public class ArticleCssFragment extends BaseFragment {

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        // TODO: 2020/2/5 0005 Css分类的实现
        return layoutInflater.inflate(R.layout.fragment_css_article, container, false);
    }
}
