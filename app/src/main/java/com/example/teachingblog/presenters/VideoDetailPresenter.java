package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IVideoDetailPresenter;
import com.example.teachingblog.interfaces.IVideoDetailViewCallback;
import com.example.teachingblog.models.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailPresenter implements IVideoDetailPresenter {

    private List<IVideoDetailViewCallback> mCallbacks = new ArrayList<>();
    //与目标视频类型相同的视频列表
    private List<Video> mTargetVideoList = new ArrayList<>();
    private Video mTargetVideo = null;

    private VideoDetailPresenter() {
    }

    private static VideoDetailPresenter sInstants = null;

    public static VideoDetailPresenter getInstance() {
        if (sInstants == null) {
            synchronized (VideoDetailPresenter.class) {
                if (sInstants == null) {
                    sInstants = new VideoDetailPresenter();
                }
            }
        }
        return sInstants;
    }

    @Override
    public void registerViewCallback(IVideoDetailViewCallback iVideoDetailViewCallback) {
        if (!mCallbacks.contains(iVideoDetailViewCallback)) {
            mCallbacks.add(iVideoDetailViewCallback);
            if (mTargetVideo != null && mTargetVideoList != null) {
                iVideoDetailViewCallback.onVideoLoaded(mTargetVideo, mTargetVideoList);
            }
        }
    }

    @Override
    public void unRegisterViewCallback(IVideoDetailViewCallback iVideoDetailViewCallback) {
        if (mCallbacks != null) {
            mCallbacks.remove(iVideoDetailViewCallback);
        }
    }

    /**
     * 设置目标视频
     *
     * @param targetVideo
     */
    public void setTargetVideo(Video targetVideo, List<Video> mTargetVideoList) {
        this.mTargetVideo = targetVideo;
        this.mTargetVideoList = mTargetVideoList;
    }
}
