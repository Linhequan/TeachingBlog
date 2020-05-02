package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IArticleLifePresenter extends IBasePresenter<IArticleLifeViewCallback> {

    /**
     * 获取Life类别的文章
     */
    void getLifeArticle();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
