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
    void onRecommendListLoaded(List<Article> result);

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
