package com.example.teachingblog.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachingblog.R;
import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.RequestCenter;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    public static final String BASE_URL = "http://47.100.137.31:3003";
    private List<Article> mDatas = new ArrayList<>();

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.fragment_home, container, false);
        // TODO: 2020/1/11 0011 封装Okhttp完成,能在里面解析TypeToken
        // TODO: 2020/1/11 0011 做View

        RequestCenter.getHomeRecommendArticle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                if (responseObj != null) {
                    mDatas = (List<Article>) responseObj;
                    LogUtil.d(TAG, "length --- > " + mDatas.size());
                    for (Article data : mDatas) {
                        LogUtil.d(TAG, data.toString());
                    }
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                if (reasonObj instanceof OkHttpException) {
                    OkHttpException exception = (OkHttpException) reasonObj;
                    LogUtil.d(TAG, "eCode --- > " + exception.getEcode());
                }
            }
        });
        return view;
    }
}
