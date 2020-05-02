package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IArticleCssPresenter extends IBasePresenter<IArticleCssViewCallback> {

    /**
     * 获取Css类别的文章
     */
    void getCssArticle();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
