package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IArticleJavascriptPresenter extends IBasePresenter<IArticleJavascriptViewCallback> {

    /**
     * 获取javascript类别的文章
     */
    void getJavascriptArticle();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
