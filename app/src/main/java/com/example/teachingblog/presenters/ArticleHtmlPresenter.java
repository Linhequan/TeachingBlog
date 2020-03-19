package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IArticleHtmlPresenter;
import com.example.teachingblog.interfaces.IArticleHtmlViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ArticleHtmlPresenter implements IArticleHtmlPresenter {

    private static final String TAG = "ArticleHtmlPresenter";
    private List<IArticleHtmlViewCallback> mCallbacks = new ArrayList<>();

    private ArticleHtmlPresenter() {
    }

    private static ArticleHtmlPresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static ArticleHtmlPresenter getInstance() {
        if (sInstance == null) {
            synchronized (ArticleHtmlPresenter.class) {
                if (sInstance == null) {
                    sInstance = new ArticleHtmlPresenter();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取分类为html的所有文章
     */
    @Override
    public void getHtmlArticle() {
        //通知UI更新正在加载
        updateLoading();
        RequestCenter.getHtmlArticle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj != null) {
                    List<Article> articleList = (List<Article>) responseObj;
                    LogUtil.d(TAG, "length ===== " + articleList.size());
                    handlerArticleResult(articleList);
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

    private void handlerError() {
        //通知UI更新网络出错
        for (IArticleHtmlViewCallback callback : mCallbacks) {
            callback.onNetworkError();
        }
    }

    private void handlerArticleResult(List<Article> articleList) {
        //通知UI更新
        if (articleList != null) {
            if (articleList.size() == 0) {
                for (IArticleHtmlViewCallback callback : mCallbacks) {
                    //回调UI数据为空
                    callback.onEmpty();
                }
            } else {
                for (IArticleHtmlViewCallback callback : mCallbacks) {
                    //回调UI数据
                    callback.onArticleListLoaded(articleList);
                }
            }
        }
    }

    private void updateLoading() {
        for (IArticleHtmlViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IArticleHtmlViewCallback iArticleHtmlViewCallback) {
        //防止重复加入
        if (mCallbacks != null && !mCallbacks.contains(iArticleHtmlViewCallback)) {
            mCallbacks.add(iArticleHtmlViewCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IArticleHtmlViewCallback iArticleHtmlViewCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iArticleHtmlViewCallback);
        }
    }
}
