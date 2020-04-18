package com.example.teachingblog.interfaces;

import com.example.teachingblog.models.Article;

import java.util.List;

/**
 * 逻辑层通知界面更新的接口
 */
public interface IHomeViewCallback {
    /**
     * 获取推荐内容的结果
     *
     * @param result
     */
    void onArticleListLoaded(List<Article> result, List<Article> bannerDatas);

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
    void onLoaderMoreSuccess(List<Article> result, List<Article> bannerDatas, boolean noMoreData);

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
    void onRefreshSuccess(List<Article> result, List<Article> bannerDatas, boolean noMoreData);

    /**
     * 下拉加载更多的结果失败
     */
    void onRefreshFailure();

}
