package com.example.teachingblog.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachingblog.R;
import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    public static final String BASE_URL = "http://47.100.137.31:3003";
    private List<Article> mDatas = new ArrayList<>();

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.fragment_home, container, false);
        // TODO: 2020/1/11 0011 封装Okhttp
        //home数据加载
        //1、创建client，理解为创建浏览器
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();

        //2、创建请求内容
        Request request = new Request.Builder()
                .url(BASE_URL + "/article")
                .get()
                .build();

        //3、用浏览器创建调用任务
        Call call = okHttpClient.newCall(request);
        //4、执行任务
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                LogUtil.d(TAG, "onFailure --- >" + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    String result = response.body().string();
                    if (result != null && result.length() > 0) {
                        Gson gson = new Gson();
                        mDatas = gson.fromJson(result, new TypeToken<List<Article>>() {
                        }.getType());
                        LogUtil.d(TAG, "length --- > " + mDatas.size());
                        for (Article data : mDatas) {
                            LogUtil.d(TAG, data.toString());
                        }
                    }
                }
            }
        });
        return view;
    }
}
