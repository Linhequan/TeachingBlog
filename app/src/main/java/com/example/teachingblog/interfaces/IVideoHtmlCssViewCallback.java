package com.example.teachingblog.interfaces;

import com.example.teachingblog.models.Video;

import java.util.List;

public interface IVideoHtmlCssViewCallback {
    /**
     * 获取Html/Css视频
     *
     * @param result
     */
    void onVideoListLoaded(List<Video> result);

    /**
     * 网络错误
     */
    void onNetworkError();

    /**
     * 数据为空
     */
    void onEmpty();

    /**
     * 正在加载
     */
    void onLoading();
}
