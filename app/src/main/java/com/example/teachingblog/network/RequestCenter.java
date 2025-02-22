package com.example.teachingblog.network;

import com.example.teachingblog.models.Article;
import com.example.teachingblog.models.Articles;
import com.example.teachingblog.models.Video;
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
        //*************************************文章相关Api**************************************************//
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

        /**
         * 查看分类为Css的所有文章接口
         */
        public static final String CSS_ARTICLE = BASE_URL + "/article/css";

        /**
         * 查看分类为javascript的所有文章接口
         */
        public static final String JAVASCRIPT_ARTICLE = BASE_URL + "/article/javascript";

        /**
         * 查看分类为vue的所有文章接口
         */
        public static final String VUE_ARTICLE = BASE_URL + "/article/vue";

        /**
         * 查看分类为node的所有文章接口
         */
        public static final String NODE_ARTICLE = BASE_URL + "/article/nodejs";

        /**
         * 查看分类为node的所有文章接口
         */
        public static final String PHP_ARTICLE = "http://47.100.137.31:3001/articles/1/5/php";

        /**
         * 查看分类为Linux的所有文章接口
         */
        public static final String LINUX_ARTICLE = "http://47.100.137.31:3001/articles/1/5/linux";

        /**
         * 查看分类为life的所有文章接口
         */
        public static final String LIFE_ARTICLE = BASE_URL + "/article/life";
        //*************************************视频相关Api**************************************************//
        /**
         * 查看分类为HTML/CSS的所有视频接口
         */
        public static final String HTML_CSS_VIDEO = BASE_URL + "/videos/type/HTMLCSS";

        /**
         * 查看分类为javascript的所有视频接口
         */
        public static final String JAVASCRIPT_VIDEO = BASE_URL + "/videos/type/JavaScript";

        /**
         * 查看分类为vue的所有视频接口
         */
        public static final String VUE_VIDEO = BASE_URL + "/videos/type/Vue";

        /**
         * 查看分类为node的所有视频接口
         */
        public static final String NODE_VIDEO = BASE_URL + "/videos/type/Nodejs";
    }

    //**************************************请求方式相关************************************************//
    //根据使用TypeToken进行解析发送get请求
    public static void getRequestByTypeToken(String url, DisposeDataListener listener, Type type) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url), new DisposeDataHandle(listener, type));
    }

    //发送get请求，并且不进行Gson解析直接返回给Ui层
    public static void getRequest(String url, DisposeDataListener listener) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url), new DisposeDataHandle(listener));
    }

    //根据使用字节码进行解析发送get请求
    public static void getRequestByClazz(String url, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url), new DisposeDataHandle(listener, clazz));
    }

    //**************************************请求文章相关************************************************//

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

    /**
     * 获取分类为Css的所有文章
     *
     * @param listener
     */
    public static void getCssArticle(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.CSS_ARTICLE, listener, new TypeToken<List<Article>>() {
        }.getType());
    }

    /**
     * 获取分类为javascript的所有文章
     *
     * @param listener
     */
    public static void getJavascriptArticle(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.JAVASCRIPT_ARTICLE, listener, new TypeToken<List<Article>>() {
        }.getType());
    }

    /**
     * 获取分类为vue的所有文章
     *
     * @param listener
     */
    public static void getVueArticle(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.VUE_ARTICLE, listener, new TypeToken<List<Article>>() {
        }.getType());
    }

    /**
     * 获取分类为node的所有文章
     *
     * @param listener
     */
    public static void getNodeArticle(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.NODE_ARTICLE, listener, new TypeToken<List<Article>>() {
        }.getType());
    }

    /**
     * 获取分类为PHP的所有文章
     *
     * @param listener
     */
    public static void getPHPArticle(DisposeDataListener listener) {
        getRequestByClazz(HttpConstants.PHP_ARTICLE, listener, Articles.class);
    }

    /**
     * 获取分类为Linux的所有文章
     *
     * @param listener
     */
    public static void getLinuxArticle(DisposeDataListener listener) {
        getRequestByClazz(HttpConstants.LINUX_ARTICLE, listener, Articles.class);
    }

    /**
     * 获取分类为life的所有文章
     *
     * @param listener
     */
    public static void getLifeArticle(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.LIFE_ARTICLE, listener, new TypeToken<List<Article>>() {
        }.getType());
    }

    //**************************************请求视频相关************************************************//

    /**
     * 获取分类为Html/Css的所有视频
     * @param listener
     */
    public static void getHtmlCssVideo(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.HTML_CSS_VIDEO, listener, new TypeToken<List<Video>>() {
        }.getType());
    }

    /**
     * 获取分类为JavaScript的所有视频
     * @param listener
     */
    public static void getJavaScriptVideo(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.JAVASCRIPT_VIDEO, listener, new TypeToken<List<Video>>() {
        }.getType());
    }

    /**
     * 获取分类为vue的所有视频
     *
     * @param listener
     */
    public static void getVueVideo(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.VUE_VIDEO, listener, new TypeToken<List<Video>>() {
        }.getType());
    }

    /**
     * 获取分类为node的所有视频
     *
     * @param listener
     */
    public static void getNodeVideo(DisposeDataListener listener) {
        getRequestByTypeToken(HttpConstants.NODE_VIDEO, listener, new TypeToken<List<Video>>() {
        }.getType());
    }
}
