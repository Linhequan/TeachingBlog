package com.example.teachingblog.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachingblog.R;
import com.example.teachingblog.base.BaseFragment;

public class CssFragment extends BaseFragment {

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        return layoutInflater.inflate(R.layout.fragment_css, container, false);
    }
}
