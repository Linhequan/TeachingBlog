package com.example.teachingblog.utils;

import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.ui.fragments.VideoHtmlCssFragment;
import com.example.teachingblog.ui.fragments.VideoJavascriptFragment;
import com.example.teachingblog.ui.fragments.VideoNodeFragment;
import com.example.teachingblog.ui.fragments.VideoVueFragment;

import java.util.HashMap;
import java.util.Map;

public class VideoFragmentCreator {
    public final static int INDEX_HTML_CSS = 0;
    public final static int INDEX_JAVASCRIPT = 1;
    public final static int INDEX_VUE = 2;
    public final static int INDEX_NODE = 3;
    public final static int PAGE_COUNT = 4;
    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (index) {
            case INDEX_HTML_CSS:
                baseFragment = new VideoHtmlCssFragment();
                break;
            case INDEX_JAVASCRIPT:
                baseFragment = new VideoJavascriptFragment();
                break;
            case INDEX_VUE:
                baseFragment = new VideoVueFragment();
                break;
            case INDEX_NODE:
                baseFragment = new VideoNodeFragment();
                break;
        }
        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
