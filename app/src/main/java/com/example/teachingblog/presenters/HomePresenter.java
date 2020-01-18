package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IHomePresenter;
import com.example.teachingblog.interfaces.IHomeViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements IHomePresenter {

    private static final String TAG = "HomePresenter";
    private List<IHomeViewCallback> mCallbacks = new ArrayList<>();

    private HomePresenter() {
    }

    private static HomePresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static HomePresenter getInstance() {
        if (sInstance == null) {
            synchronized (HomePresenter.class) {
                if (sInstance == null) {
                    sInstance = new HomePresenter();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取首页推荐文章
     */
    @Override
    public void getHomeRecommendArticle() {
        //通知UI更新正在加载
        updateLoading();
        RequestCenter.getHomeRecommendArticle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                LogUtil.d(TAG, "thread name ---->" + Thread.currentThread().getName());
                if (responseObj != null) {
                    List<Article> articleList = (List<Article>) responseObj;
                    LogUtil.d(TAG, "length --- > " + articleList.size());
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
        for (IHomeViewCallback callback : mCallbacks) {
            callback.onNetworkError();
        }
    }

    private void updateLoading() {
        for (IHomeViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    private void handlerRecommendResult(List<Article> articleList) {
        //通知UI更新
        if (articleList != null) {
            if (articleList.size() == 0) {
                for (IHomeViewCallback callback : mCallbacks) {
                    //回调UI数据为空
                    callback.onEmpty();
                }
            } else {

                for (IHomeViewCallback callback : mCallbacks) {
                    //回调UI数据
                    callback.onArticleListLoaded(articleList);
                }
            }
        }
    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IHomeViewCallback iHomeViewCallback) {
        //防止重复加入
        if (mCallbacks != null && !mCallbacks.contains(iHomeViewCallback)) {
            mCallbacks.add(iHomeViewCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IHomeViewCallback iHomeViewCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iHomeViewCallback);
        }
    }
}
