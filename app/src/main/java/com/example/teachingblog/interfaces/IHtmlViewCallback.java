package com.example.teachingblog.interfaces;

import com.example.teachingblog.models.Article;

import java.util.List;

public interface IHtmlViewCallback {

    /**
     * 获取Html文章
     *
     * @param result
     */
    void onArticleListLoaded(List<Article> result);

    /**
     * 网络错误
     */
    void onNetworkError();

    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * 正在加载
     */
    void onLoading();
}
