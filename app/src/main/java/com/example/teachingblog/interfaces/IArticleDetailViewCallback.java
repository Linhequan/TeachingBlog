package com.example.teachingblog.interfaces;

import com.example.teachingblog.models.Article;

public interface IArticleDetailViewCallback {

    /**
     * 把Article传给UI使用
     *
     * @param article 文章
     */
    void onArticleLoaded(Article article);

    /**
     * 文章详情内容加载出来了
     *
     * @param result 该文章的html和js
     */
    void onArticleDetailLoaded(String result);

    /**
     * 网络错误
     */
    void onNetworkError();

    /**
     * 正在加载
     */
    void onLoading();
}
