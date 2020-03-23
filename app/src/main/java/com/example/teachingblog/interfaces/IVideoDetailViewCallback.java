package com.example.teachingblog.interfaces;

import com.example.teachingblog.models.Video;

import java.util.List;

public interface IVideoDetailViewCallback {

    /**
     * 把Video传给Ui使用
     *
     * @param video
     * @param videos
     */
    void onVideoLoaded(Video video, List<Video> videos);

}
