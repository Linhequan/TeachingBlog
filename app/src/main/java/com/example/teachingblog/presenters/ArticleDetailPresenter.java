package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IArticleDetailPresenter;
import com.example.teachingblog.interfaces.IArticleDetailViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ArticleDetailPresenter implements IArticleDetailPresenter {

    private static final String TAG = "ArticleDetailPresenter";
    private List<IArticleDetailViewCallback> mCallbacks = new ArrayList<>();
    private Article mTargetArticle = null;

    private ArticleDetailPresenter() {
    }

    private static ArticleDetailPresenter sInstance = null;

    public static ArticleDetailPresenter getInstance() {
        if (sInstance == null) {
            synchronized (ArticleDetailPresenter.class) {
                if (sInstance == null) {
                    sInstance = new ArticleDetailPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void getArticleDetail(int articleId) {
        //通知UI更新正在加载
        updateLoading();
        //根据id获取文章的markdown内容
        RequestCenter.getArticleDetailMarkdown(articleId, new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj != null) {
                    String result = (String) responseObj;
                    LogUtil.d(TAG, "result --- > " + result);
                    handlerMarkdownResult(result);
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof OkHttpException) {
                    OkHttpException exception = (OkHttpException) reasonObj;
                    LogUtil.d(TAG, "eCode --- > " + exception.getEcode());
                    handlerError();
                }
            }
        });
    }

    private void handlerMarkdownResult(String result) {
        //通知UI更新
        if (result != null && result.length() > 0) {
            for (IArticleDetailViewCallback callback : mCallbacks) {
                //回调UI数据
                callback.onArticleDetailLoaded(result);
            }
        }
    }

    private void handlerError() {
        //通知UI更新网络出错
        for (IArticleDetailViewCallback callback : mCallbacks) {
            callback.onNetworkError();
        }
    }

    private void updateLoading() {
        for (IArticleDetailViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    @Override
    public void registerViewCallback(IArticleDetailViewCallback detailViewCallback) {
        if (!mCallbacks.contains(detailViewCallback)) {
            mCallbacks.add(detailViewCallback);
            if (mTargetArticle != null) {
                detailViewCallback.onArticleLoaded(mTargetArticle);
            }
        }
    }

    @Override
    public void unRegisterViewCallback(IArticleDetailViewCallback iArticleDetailViewCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iArticleDetailViewCallback);
        }
    }

    /**
     * 设置目标文章
     *
     * @param targetArticle
     */
    public void setTargetArticle(Article targetArticle) {
        this.mTargetArticle = targetArticle;
    }
}
