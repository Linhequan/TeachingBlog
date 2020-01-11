package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IHomePresenter extends IBasePresenter<IHomeViewCallback> {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
