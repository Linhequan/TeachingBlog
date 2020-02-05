package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IHtmlPresenter;
import com.example.teachingblog.interfaces.IHtmlViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HtmlPresenter implements IHtmlPresenter {

    private static final String TAG = "HtmlPresenter";
    private List<IHtmlViewCallback> mCallbacks = new ArrayList<>();

    private HtmlPresenter() {
    }

    private static HtmlPresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static HtmlPresenter getInstance() {
        if (sInstance == null) {
            synchronized (HtmlPresenter.class) {
                if (sInstance == null) {
                    sInstance = new HtmlPresenter();
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
                    handlerRecommendResult(articleList);
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
        for (IHtmlViewCallback callback : mCallbacks) {
            callback.onNetworkError();
        }
    }

    private void handlerRecommendResult(List<Article> articleList) {
        //通知UI更新
        if (articleList != null) {
            if (articleList.size() == 0) {
                for (IHtmlViewCallback callback : mCallbacks) {
                    //回调UI数据为空
                    callback.onEmpty();
                }
            } else {
                for (IHtmlViewCallback callback : mCallbacks) {
                    //回调UI数据
                    callback.onArticleListLoaded(articleList);
                }
            }
        }
    }

    private void updateLoading() {
        for (IHtmlViewCallback callback : mCallbacks) {
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
    public void registerViewCallback(IHtmlViewCallback iHtmlViewCallback) {
        //防止重复加入
        if (mCallbacks != null && !mCallbacks.contains(iHtmlViewCallback)) {
            mCallbacks.add(iHtmlViewCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IHtmlViewCallback iHtmlViewCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iHtmlViewCallback);
        }
    }
}
