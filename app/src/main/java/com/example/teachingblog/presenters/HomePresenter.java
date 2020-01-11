package com.example.teachingblog.presenters;

import com.example.teachingblog.interfaces.IHomePresenter;
import com.example.teachingblog.interfaces.IHomeViewCallback;

public class HomePresenter implements IHomePresenter {

    private HomePresenter() {
    }

    private static HomePresenter sInstance = null;

    /**
     * 获取单例对象
     *
     * @return
     */
    public HomePresenter getInstance() {
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
    public void getRecommendList() {

    }

    @Override
    public void pull2RefreshMore() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallback(IHomeViewCallback iHomeViewCallback) {

    }

    @Override
    public void unRegisterViewCallback(IHomeViewCallback iHomeViewCallback) {

    }
}
