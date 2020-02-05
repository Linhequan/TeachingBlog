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
         * 查看所有文章接口
         */
        public static final String HOME__ARTICLE = BASE_URL + "/article";

        /**
         * 根据文章id获取文章的markdown内容接口
         */
        public static final String ARTICLE_DETAIL_MARKDOWN = BASE_URL + "/article/get/";

        /**
         * 查看分类为html的所有文章接口
         */
        public static final String HTML_ARTICLE = BASE_URL + "/article/html";

    }

    //根据使用TypeToken进行解析发送get请求
    public static void getRequestByTypeToken(String url, DisposeDataListener listener, Type type) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url), new DisposeDataHandle(listener, type));
    }

    //发送get请求，并且不进行Gson解析直接返回给Ui层
    public static void getRequest(String url, DisposeDataListener listener) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url), new DisposeDataHandle(listener));
    }

    /**
     * 获取首页推荐文章
     *
     * @param listener
     */
    public static void getHomeArticle(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.HOME__ARTICLE, listener, new TypeToken<List<Article>>() {
        }.getType());
    }

    /**
     * 根据id获取文章的markdown内容
     *
     * @param id       文章id
     * @param listener
     */
    public static void getArticleDetailMarkdown(int id, DisposeDataListener listener) {
        String url = HttpConstants.ARTICLE_DETAIL_MARKDOWN + id;
        getRequest(url, listener);
    }

    /**
     * 获取分类为html的所有文章
     *
     * @param listener
     */
    public static void getHtmlArticle(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.HTML_ARTICLE, listener, new TypeToken<List<Article>>() {
        }.getType());
    }
}
