package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IArticleNodePresenter extends IBasePresenter<IArticleNodeViewCallback> {

    /**
     * 获取Node类别的文章
     */
    void getNodeArticle();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
