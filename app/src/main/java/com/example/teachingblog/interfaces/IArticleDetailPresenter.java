package com.example.teachingblog.interfaces;

import com.example.teachingblog.base.IBasePresenter;

public interface IArticleDetailPresenter extends IBasePresenter<IArticleDetailViewCallback> {

    /**
     * 获取文章内容详情
     *
     * @param articleId 文章id
     */
    void getArticleDetail(int articleId);
}
