package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IArticlePHPPresenter extends IBasePresenter<IArticlePHPViewCallback> {

    /**
     * 获取PHP类别的文章
     */
    void getPHPArticle();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
