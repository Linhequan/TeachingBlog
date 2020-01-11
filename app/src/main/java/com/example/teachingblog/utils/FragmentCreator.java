package com.example.teachingblog.utils;

import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.fragments.CssFragment;
import com.example.teachingblog.fragments.HtmlFragment;
import com.example.teachingblog.fragments.JavaScriptFragment;
import com.example.teachingblog.fragments.NodeFragment;
import com.example.teachingblog.fragments.VueFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于创建Fragment和保存已经创建过的Fragment，这样就不会重复创建
 */
public class FragmentCreator {
    public final static int INDEX_HTML = 0;
    public final static int INDEX_CSS = 1;
    public final static int INDEX_JAVASCRIPT = 2;
    public final static int INDEX_VUE = 3;
    public final static int INDEX_NODE = 4;
    public final static int PAGE_COUNT = 5;
    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (index) {
            case INDEX_HTML:
                baseFragment = new HtmlFragment();
                break;
            case INDEX_CSS:
                baseFragment = new CssFragment();
                break;
            case INDEX_JAVASCRIPT:
                baseFragment = new JavaScriptFragment();
                break;
            case INDEX_VUE:
                baseFragment = new VueFragment();
                break;
            case INDEX_NODE:
                baseFragment = new NodeFragment();
                break;
        }
        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
