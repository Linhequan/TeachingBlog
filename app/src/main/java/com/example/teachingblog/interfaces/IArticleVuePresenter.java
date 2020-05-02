package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IArticleVuePresenter extends IBasePresenter<IArticleVueViewCallback> {

    /**
     * 获取Vue类别的文章
     */
    void getVueArticle();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
