package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IArticleLinuxPresenter extends IBasePresenter<IArticleLinuxViewCallback> {

    /**
     * 获取Linux类别的文章
     */
    void getLinuxArticle();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
