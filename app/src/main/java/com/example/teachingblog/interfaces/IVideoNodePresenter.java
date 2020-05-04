package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IVideoNodePresenter extends IBasePresenter<IVideoNodeViewCallback> {

    /**
     * 获取node类别的视频
     */
    void getNodeVideo();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
