package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IVideoJavascriptPresenter extends IBasePresenter<IVideoJavascriptViewCallback> {

    /**
     * 获取js类别的视频
     */
    void getJavascriptVideo();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
