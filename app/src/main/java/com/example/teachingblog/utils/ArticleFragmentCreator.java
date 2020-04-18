package com.example.teachingblog.utils;

import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.ui.fragments.ArticleHtmlFragment;
import com.example.teachingblog.ui.fragments.ArticleCssFragment;
import com.example.teachingblog.ui.fragments.ArticleJavaScriptFragment;
import com.example.teachingblog.ui.fragments.ArticleLifeFragment;
import com.example.teachingblog.ui.fragments.ArticleLinuxFragment;
import com.example.teachingblog.ui.fragments.ArticleNodeFragment;
import com.example.teachingblog.ui.fragments.ArticlePHPFragment;
import com.example.teachingblog.ui.fragments.ArticleVueFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于创建Fragment和保存已经创建过的Fragment，这样就不会重复创建
 */
public class ArticleFragmentCreator {
    public final static int INDEX_HTML = 0;
    public final static int INDEX_CSS = 1;
    public final static int INDEX_JAVASCRIPT = 2;
    public final static int INDEX_VUE = 3;
    public final static int INDEX_NODE = 4;
    public final static int INDEX_PHP = 5;
    public final static int INDEX_LINUX = 6;
    public final static int INDEX_LIFE = 7;
    public final static int PAGE_COUNT = 8;
    private static Map<Integer, BaseFragment> sCache = new HashMap<>();

    public static BaseFragment getFragment(int index) {
        BaseFragment baseFragment = sCache.get(index);
        if (baseFragment != null) {
            return baseFragment;
        }

        switch (index) {
            case INDEX_HTML:
                baseFragment = new ArticleHtmlFragment();
                break;
            case INDEX_CSS:
                baseFragment = new ArticleCssFragment();
                break;
            case INDEX_JAVASCRIPT:
                baseFragment = new ArticleJavaScriptFragment();
                break;
            case INDEX_VUE:
                baseFragment = new ArticleVueFragment();
                break;
            case INDEX_NODE:
                baseFragment = new ArticleNodeFragment();
                break;
            case INDEX_PHP:
                baseFragment = new ArticlePHPFragment();
                break;
            case INDEX_LINUX:
                baseFragment = new ArticleLinuxFragment();
                break;
            case INDEX_LIFE:
                baseFragment = new ArticleLifeFragment();
                break;
        }
        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
