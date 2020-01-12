package com.example.teachingblog.network;

import com.example.teachingblog.network.listener.DisposeDataHandle;
import com.example.teachingblog.network.response.CommonFileCallback;
import com.example.teachingblog.network.response.CommonJsonCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 用来发送get, post请求的工具类，包括设置一些请求的共用参数
 */
public class CommonOkHttpClient {
    private static final int TIME_OUT = 30;
    private static OkHttpClient mOkHttpClient;

    //完成对OkhttpClient的初始化
    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //设置对所有域名都是信任的
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        /**
         *  为所有请求添加请求头，看个人需求
         */
//        okHttpClientBuilder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request =
//                        chain.request().newBuilder().addHeader("User-Agent", "Imooc-Mobile") // 标明发送本次请求的客户端
//                                .build();
//                return chain.proceed(request);
//            }
//        });
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        //设置重定向为true
//        okHttpClientBuilder.followRedirects(true);

        mOkHttpClient = okHttpClientBuilder.build();
    }

    /**
     * 通过构造好的Request,Callback去发送请求
     */
    /**
     * get请求
     *
     * @param request 通过CommonRequest创建一个get的request
     * @param handle
     * @return
     */
    public static Call get(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    /**
     * post请求
     *
     * @param request 通过CommonRequest创建一个post的request
     * @param handle
     * @return
     */
    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    /**
     * 文件下载
     *
     * @param request
     * @param handle
     * @return
     */
    public static Call downloadFile(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }
}
