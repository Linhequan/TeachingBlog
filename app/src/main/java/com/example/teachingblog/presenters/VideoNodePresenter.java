package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IVideoNodePresenter;
import com.example.teachingblog.interfaces.IVideoNodeViewCallback;
import com.example.teachingblog.models.Video;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.Constants;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class VideoNodePresenter implements IVideoNodePresenter {

    private static final String TAG = "VideoNodePresenter";
    private List<IVideoNodeViewCallback> mCallbacks = new ArrayList<>();
    private List<Video> mVideos = new ArrayList<>();

    //当前页
    private int mCurrentPageIndex = 0;
    //前一次的页数
    private int mPreviousPageIndex = 0;

    private VideoNodePresenter() {
    }

    private static VideoNodePresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static VideoNodePresenter getInstance() {
        if (sInstance == null) {
            synchronized (VideoNodePresenter.class) {
                if (sInstance == null) {
                    sInstance = new VideoNodePresenter();
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
     * 获取分类为node的视频
     */
    @Override
    public void getNodeVideo() {
        mVideos.clear();
        this.mCurrentPageIndex = 1;
        //通知UI更新正在加载
        updateLoading();
        doLoaded(Constants.NORMAL);
    }

    private void doLoaded(int loadType) {
        //获取分类为node的视频
        RequestCenter.getNodeVideo(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj != null) {
                    List<Video> videoList = (List<Video>) responseObj;
                    LogUtil.d(TAG, "length ===== " + videoList.size());
                    //对该List进行分页
                    List<Video> videos = Utils.getVideoListPage(mCurrentPageIndex, Constants.VIDEO_CLASSIFICATION_COUNT, videoList);
                    LogUtil.d(TAG, "videos length --- > " + videos.size());
                    switch (loadType) {
                        case Constants.LOADER_MORE:
                            //上拉加载，结果放到后面去
                            mVideos.addAll(videos);
                            if (videos.size() == 0) {
                                mCurrentPageIndex--;
                                handlerLoaderMoreResult(true);
                            } else {
                                handlerLoaderMoreResult(videos.size() < Constants.VIDEO_CLASSIFICATION_COUNT);
                            }
                            break;
                        case Constants.REFRESH_MORE:
                            //这个是下拉加载，结果放到前面去
                            //先清空
                            mVideos.clear();
                            mVideos.addAll(videos);
                            handlerRefreshMore(videos.size() < Constants.VIDEO_CLASSIFICATION_COUNT);
                            break;
                        case Constants.NORMAL:
                            //普通加载
                            mVideos.addAll(videos);
                            handlerVideoResult(videos.size() < Constants.VIDEO_CLASSIFICATION_COUNT);
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
        if (mVideos != null) {
            for (IVideoNodeViewCallback callback : mCallbacks) {
                callback.onRefreshSuccess(mVideos, noMoreData);
            }
        }
    }

    /**
     * 处理下拉刷新失败
     */
    private void handlerRefreshMoreError() {
        for (IVideoNodeViewCallback callback : mCallbacks) {
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
        if (mVideos != null) {
            for (IVideoNodeViewCallback callback : mCallbacks) {
                callback.onLoaderMoreSuccess(mVideos, noMoreData);
            }
        }
    }

    /**
     * 处理上拉加载更多失败
     */
    private void handlerLoaderMoreError() {
        for (IVideoNodeViewCallback callback : mCallbacks) {
            callback.onLoaderMoreFailure();
        }
    }

    /**
     * 处理加载node分类视频数据的结果
     *
     * @param noMoreData 是否有更多数据
     */
    private void handlerVideoResult(boolean noMoreData) {
        //通知UI更新
        if (mVideos != null) {
            if (mVideos.size() == 0) {
                for (IVideoNodeViewCallback callback : mCallbacks) {
                    //回调UI数据为空
                    callback.onEmpty();
                }
            } else {
                for (IVideoNodeViewCallback callback : mCallbacks) {
                    //回调UI数据
                    callback.onVideoListLoaded(mVideos, noMoreData);
                }
            }
        }
    }

    private void handlerError() {
        //通知UI更新网络出错
        for (IVideoNodeViewCallback callback : mCallbacks) {
            callback.onNetworkError();
        }
    }

    private void updateLoading() {
        for (IVideoNodeViewCallback callback : mCallbacks) {
            callback.onLoading();
        }
    }

    @Override
    public void registerViewCallback(IVideoNodeViewCallback iVideoNodeViewCallback) {
        //防止重复加入
        if (mCallbacks != null && !mCallbacks.contains(iVideoNodeViewCallback)) {
            mCallbacks.add(iVideoNodeViewCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IVideoNodeViewCallback iVideoNodeViewCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iVideoNodeViewCallback);
        }
    }
}
