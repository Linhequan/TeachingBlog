package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IHomePresenter;
import com.example.teachingblog.interfaces.IHomeViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.Constants;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements IHomePresenter {

    private static final String TAG = "HomePresenter";
    private List<IHomeViewCallback> mCallbacks = new ArrayList<>();
    private List<Article> mArticles = new ArrayList<>();

    //当前页
    private int mCurrentPageIndex = 0;
    //前一次的页数
    private int mPreviousPageIndex = 0;

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

    @Override
    public void pull2RefreshMore() {
        //下拉加载更多
        //保存之前的页数
        mPreviousPageIndex = mCurrentPageIndex;
        this.mCurrentPageIndex = 1;
        doLoaded(Constants.REFRESH_MORE);
    }

    @Override
    public void loadMore() {
        //去上拉加载更多内容
        mCurrentPageIndex++;
        //传入true，表示结果会追加到列表的后方。
        doLoaded(Constants.LOADER_MORE);
    }

    /**
     * 获取首页推荐文章
     */
    @Override
    public void getHomeArticle() {
        mArticles.clear();
        this.mCurrentPageIndex = 1;
        //通知UI更新正在加载
        updateLoading();
        doLoaded(Constants.NORMAL);
    }


    private void doLoaded(int loadType) {
        //获取首页推荐文章
        RequestCenter.getHomeArticle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                LogUtil.d(TAG, "thread name ---->" + Thread.currentThread().getName());
                if (responseObj != null) {
                    List<Article> articleList = (List<Article>) responseObj;
                    LogUtil.d(TAG, "articleList length --- > " + articleList.size());
                    //对该List进行分页
                    List<Article> articles = Utils.getArticleListPage(mCurrentPageIndex, Constants.HOME_COUNT, articleList);
                    LogUtil.d(TAG, "articles length --- > " + articles.size());
                    //Banner数据
                    List<Article> bannerDatas = Utils.getBannerDatas(articleList);
                    LogUtil.d(TAG, "bannerDatas length --- > " + bannerDatas.size());
                    switch (loadType) {
                        case Constants.LOADER_MORE:
                            //上拉加载，结果放到后面去
                            mArticles.addAll(articles);
                            if (articles.size() == 0) {
                                mCurrentPageIndex--;
                                handlerLoaderMoreResult(true, bannerDatas);
                            } else {
                                handlerLoaderMoreResult(articles.size() < Constants.HOME_COUNT, bannerDatas);
                            }
                            break;
                        case Constants.REFRESH_MORE:
                            //这个是下拉加载，结果放到前面去
                            //先清空
                            mArticles.clear();
                            mArticles.addAll(articles);
                            handlerRefreshMore(articles.size() < Constants.HOME_COUNT, bannerDatas);
                            break;
                        case Constants.NORMAL:
                            //普通加载
                            mArticles.addAll(articles);
                            handlerRecommendResult(bannerDatas);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof OkHttpException) {
                    OkHttpException exception = (OkHttpException) reasonObj;
                    LogUtil.d(TAG, "eCode --- > " + exception.getEcode());
                    switch (loadType) {
                        case Constants.LOADER_MORE:
                            //恢复先前的状态
                            mCurrentPageIndex--;
                            handlerLoaderMoreError();
                            break;
                        case Constants.REFRESH_MORE:
                            //恢复先前的状态
                            mCurrentPageIndex = mPreviousPageIndex;
                            handlerRefreshMoreError();
                            break;
                        case Constants.NORMAL:
                            handlerError();
                            break;
                    }
                }
            }
        });
    }

    /**
     * 处理下拉刷新的结果
     *
     * @param noMoreData  是否有更多数据
     * @param bannerDatas 轮播图数据
     */
    private void handlerRefreshMore(boolean noMoreData, List<Article> bannerDatas) {
        if (mArticles != null && bannerDatas != null) {
            for (IHomeViewCallback callback : mCallbacks) {
                callback.onRefreshSuccess(mArticles, bannerDatas, noMoreData);
            }
        }
    }

    /**
     * 处理下拉刷新失败
     */
    private void handlerRefreshMoreError() {
        for (IHomeViewCallback callback : mCallbacks) {
            callback.onRefreshFailure();
        }
    }

    /**
     * 处理上拉加载更多的结果
     *
     * @param noMoreData  是否有更多数据
     * @param bannerDatas 轮播图数据
     */
    private void handlerLoaderMoreResult(boolean noMoreData, List<Article> bannerDatas) {
        //通知UI更新
        if (mArticles != null && bannerDatas != null) {
            for (IHomeViewCallback callback : mCallbacks) {
                callback.onLoaderMoreSuccess(mArticles, bannerDatas, noMoreData);
            }
        }
    }

    /**
     * 处理上拉加载更多失败
     */
    private void handlerLoaderMoreError() {
        for (IHomeViewCallback callback : mCallbacks) {
            callback.onLoaderMoreFailure();
        }
    }

    /**
     * 处理加载首页数据的结果
     *
     * @param bannerDatas 轮播图数据
     */
    private void handlerRecommendResult(List<Article> bannerDatas) {
        //通知UI更新
        if (mArticles != null && bannerDatas != null) {
            if (mArticles.size() == 0 || bannerDatas.size() == 0) {
                for (IHomeViewCallback callback : mCallbacks) {
                    //回调UI数据为空
                    callback.onEmpty();
                }
            } else {
                for (IHomeViewCallback callback : mCallbacks) {
                    //回调UI数据
                    callback.onArticleListLoaded(mArticles, bannerDatas);
                }
            }
        }
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
