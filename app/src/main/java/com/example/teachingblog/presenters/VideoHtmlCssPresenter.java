package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IVideoHtmlCssPresenter;
import com.example.teachingblog.interfaces.IVideoHtmlCssViewCallback;
import com.example.teachingblog.models.Video;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class VideoHtmlCssPresenter implements IVideoHtmlCssPresenter {

    private static final String TAG = "VideoHtmlCssPresenter";
    private List<IVideoHtmlCssViewCallback> mCallbacks = new ArrayList<>();

    private VideoHtmlCssPresenter() {
    }

    private static VideoHtmlCssPresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public static VideoHtmlCssPresenter getInstance() {
        if (sInstance == null) {
            synchronized (VideoHtmlCssPresenter.class) {
                if (sInstance == null) {
                    sInstance = new VideoHtmlCssPresenter();
                }
            }
        }
        return sInstance;
    }

    @Override
    public void getHtmlCssVideo() {
        //通知UI更新正在加载
        updateLoading();
        RequestCenter.getHtmlCssVideo(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj != null) {
                    List<Video> videoList = (List<Video>) responseObj;
                    LogUtil.d(TAG, "length ===== " + videoList.size());
                    handlerVideoResult(videoList);
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
        for (IVideoHtmlCssViewCallback callback : mCallbacks) {
            callback.onNetworkError();
        }
    }

    private void handlerVideoResult(List<Video> videoList) {
        //通知UI更新
        if (videoList != null) {
            if (videoList.size()==0) {
                for (IVideoHtmlCssViewCallback callback : mCallbacks) {
                    //回调UI数据为空
                    callback.onEmpty();
                }
            } else {
                for (IVideoHtmlCssViewCallback callback : mCallbacks) {
                    //回调UI数据
                    callback.onVideoListLoaded(videoList);
                }
            }
        }
    }

    private void updateLoading() {
        for (IVideoHtmlCssViewCallback callback : mCallbacks) {
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
    public void registerViewCallback(IVideoHtmlCssViewCallback iVideoHtmlCssViewCallback) {
        //防止重复加入
        if (mCallbacks != null && !mCallbacks.contains(iVideoHtmlCssViewCallback)) {
            mCallbacks.add(iVideoHtmlCssViewCallback);
        }
    }

    @Override
    public void unRegisterViewCallback(IVideoHtmlCssViewCallback iVideoHtmlCssViewCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iVideoHtmlCssViewCallback);
        }
    }
}
