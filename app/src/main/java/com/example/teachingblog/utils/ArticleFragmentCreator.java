package com.example.teachingblog.utils;

import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.fragments.ArticleHtmlFragment;
import com.example.teachingblog.fragments.ArticleBookRecommendFragment;
import com.example.teachingblog.fragments.ArticleCssFragment;
import com.example.teachingblog.fragments.ArticleJavaScriptFragment;
import com.example.teachingblog.fragments.ArticleLifeFragment;
import com.example.teachingblog.fragments.ArticleNodeFragment;
import com.example.teachingblog.fragments.ArticleVueFragment;

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
    public final static int INDEX_LIFE = 5;
    public final static int INDEX_BOOK_RECOMMEND = 6;
    public final static int PAGE_COUNT = 7;
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
            case INDEX_LIFE:
                baseFragment = new ArticleLifeFragment();
                break;
            case INDEX_BOOK_RECOMMEND:
                baseFragment = new ArticleBookRecommendFragment();
                break;
        }
        sCache.put(index, baseFragment);
        return baseFragment;
    }
}
