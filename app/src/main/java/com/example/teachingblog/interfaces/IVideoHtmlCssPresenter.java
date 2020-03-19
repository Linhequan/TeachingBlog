package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IVideoHtmlCssPresenter extends IBasePresenter<IVideoHtmlCssViewCallback> {

    /**
     * 获取Html/Css类别的视频
     */
    void getHtmlCssVideo();

    /**
     * 下拉刷新更多内容
     */
    void pull2RefreshMore();

    /**
     * 上拉加载更多
     */
    void loadMore();
}
