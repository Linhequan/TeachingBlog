package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IArticleLifePresenter;
import com.example.teachingblog.interfaces.IArticleLifeViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.Constants;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ArticleLifePresenter implements IArticleLifePresenter {

    private static final String TAG = "ArticleLifePresenter";
    private List<IArticleLifeViewCallback> mCallbacks = new ArrayList<>();
    private List<Article> mArticles = new ArrayList<>();

    //当前页
    private int mCurrentPageIndex = 0;
    //前一次的页数
    private int mPreviousPageIndex = 0;

    private ArticleLifePresenter() {
    }

    private static ArticleLifePresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static ArticleLifePresenter getInstance() {
        if (sInstance == null) {
            synchronized (ArticleLifePresenter.class) {
                if (sInstance == null) {
                    sInstance = new ArticleLifePresenter();
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
     * 获取分类为life的所有文章
     */
    @Override
    public void getLifeArticle() {
        mArticles.clear();
        this.mCurrentPageIndex = 1;
        //通知UI更新正在加载
        updateLoading();
        doLoaded(Constants.NORMAL);
    }

    private void doLoaded(int loadType) {
        //获取分类为life的所有文章
        RequestCenter.getLifeArticle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj != null) {
                    List<Article> articleList = (List<Article>) responseObj;
                    LogUtil.d(TAG, "length ===== " + articleList.size());
                    //对该List进行分页
                    List<Article> articles = Utils.getArticleListPage(mCurrentPageIndex, Constants.ARTICLE_CLASSIFICATION_COUNT, articleList);
                    LogUtil.d(TAG, "articles length --- > " + articles.size());
                    switch (loadType) {
                        case Constants.LOADER_MORE:
                            //上拉加载，结果放到后面去
                            mArticles.addAll(articles);
                            if (articles.size() == 0) {
                                mCurrentPageIndex--;
                                handlerLoaderMoreResult(true);
                            } else {
                                handlerLoaderMoreResult(articles.size() < Constants.ARTICLE_CLASSIFICATION_COUNT);
                            }
                            break;
                        case Constants.REFRESH_MORE:
                            //这个是下拉加载，结果放到前面去
                            //先清空
                            mArticles.clear();
                            mArticles.addAll(articles);
                            handlerRefreshMore(articles.size() < Constants.ARTICLE_CLASSIFICATION_COUNT);
                            break;
                        case Constants.NORMAL:
                            //普通加载
                            mArticles.addAll(articles);
                            handlerArticleResult(articles.size() < Constants.ARTICLE_CLASSIFICATION_COUNT);
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
     * @param noMoreData 是否有更多数据
     */
    private void handlerRefreshMore(boolean noMoreData) {
        //通知UI更新
        if (mArticles != null) {
            for (IArticleLifeViewCallback callback : mCallbacks) {
                callback.onRefreshSuccess(mArticles, noMoreData);
            }
        }
    }

    /**
     * 处理下拉刷新失败
     */
    private void handlerRefreshMoreError() {
        for (IArticleLifeViewCallback callback : mCallbacks) {
            callback.onRefreshFailure();
        }
    }

    /**
     * 处理上拉加载更多的结果
     *
     * @param noMoreData 是否有更多数据
     */
    private void handlerLoaderMoreResult(boolean noMoreData) {
        //通知UI更新
        if (mArticles != null) {
            for (IArticleLifeViewCallback callback : mCallbacks) {
                callback.onLoaderMoreSuccess(mArticles, noMoreData);
            }
        }
    }

    /**
     * 处理上拉加载更多失败
     */
    private void handlerLoaderMoreError() {
        for (IArticleLifeViewCallback callback : mCallbacks) {
            callback.onLoaderMoreFailure();
        }
    }

    /**
     * 处理加载life分类文章数据的结果
     *
     * @param noMoreData 是否有更多数据
     */
    private void handlerArticleResult(boolean noMoreData) {
        //通知UI更新
        if (mArticles != null) {
            if (mArticles.size() == 0) {
                for (IArticleLifeViewCallback callback : mCallbacks) {
                    //回调UI数据为空
                    callback.onEmpty();
                }
            } else {
                for (IArticleLifeViewCallback callback : mCallbacks) {
                    //回调UI数据
                    callback.onArticleListLoaded(mArticles, noMoreData);
                }
            }
        }
    }

    private void handlerError() {
        //通知UI更新网络出错
        for (IArticleLifeViewCallback callback : mCallbacks) {
            callback.onNetworkError();
        }
    }

    private void updateLoading() {
        for (IArticleLifeViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    @Override
    public void registerViewCallback(IArticleLifeViewCallback iArticleLifeViewCallback) {
        //防止重复加入
        if (mCallbacks != null && !mCallbacks.contains(iArticleLifeViewCallback)) {
            mCallbacks.add(iArticleLifeViewCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IArticleLifeViewCallback iArticleLifeViewCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iArticleLifeViewCallback);
        }
    }
}
