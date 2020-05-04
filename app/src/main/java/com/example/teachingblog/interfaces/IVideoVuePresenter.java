package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IVideoVuePresenter extends IBasePresenter<IVideoVueViewCallback> {

    /**
     * 获取Vue类别的视频
     */
    void getVueVideo();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
