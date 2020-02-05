package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IHtmlPresenter extends IBasePresenter<IHtmlViewCallback> {

    /**
     * 获取Html类别的文章
     */
    void getHtmlArticle();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
