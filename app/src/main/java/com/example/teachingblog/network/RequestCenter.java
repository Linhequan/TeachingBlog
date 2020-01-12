package com.example.teachingblog.network;

import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.listener.DisposeDataHandle;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.network.request.CommonRequest;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 请求中心
 */
public class RequestCenter {

    //所有的请求api
    static class HttpConstants {
        public static final String BASE_URL = "http://47.100.137.31:3003";

        /**
         * 首页推荐文章接口
         */
        public static final String HOME_RECOMMEND_ARTICLE = BASE_URL + "/article";

    }

    //根据使用TypeToken进行解析发送post请求
    public static void getRequest(String url, DisposeDataListener listener, Type type) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url), new DisposeDataHandle(listener, type));
    }

    /**
     * 获取首页推荐文章
     *
     * @param listener
     */
    public static void getHomeRecommendArticle(DisposeDataListener listener) {
        getRequest(HttpConstants.HOME_RECOMMEND_ARTICLE, listener, new TypeToken<List<Article>>() {
        }.getType());
    }
}
