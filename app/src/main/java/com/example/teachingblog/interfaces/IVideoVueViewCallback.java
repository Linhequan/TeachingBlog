package com.example.teachingblog.interfaces;

import com.example.teachingblog.models.Video;

import java.util.List;

public interface IVideoVueViewCallback {
    /**
     * 获取Vue视频
     *
     * @param result
     * @param noMoreData 是否有更多数据
     */
    void onVideoListLoaded(List<Video> result, boolean noMoreData);

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

    /**
     * 加载更多结果成功
     *
     * @param result     目前要显示的数据
     * @param noMoreData 是否有更多数据
     */
    void onLoaderMoreSuccess(List<Video> result, boolean noMoreData);

    /**
     * 加载更多结果失败
     */
    void onLoaderMoreFailure();

    /**
     * 下拉加载更多的结果成功
     *
     * @param result     目前要显示的数据
     * @param noMoreData 是否有更多数据
     */
    void onRefreshSuccess(List<Video> result, boolean noMoreData);

    /**
     * 下拉加载更多的结果失败
     */
    void onRefreshFailure();
}
