package com.example.teachingblog.network.response;

import android.os.Handler;
import android.os.Looper;

import com.example.teachingblog.models.Article;
import com.example.teachingblog.network.exception.OkHttpException;
import com.example.teachingblog.network.listener.DisposeDataHandle;
import com.example.teachingblog.network.listener.DisposeDataListener;
import com.example.teachingblog.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @文件描述：专门处理Json回调
 */
public class CommonJsonCallback implements Callback {

    private static final String TAG = "CommonJsonCallback";

    /**
     * the logic layer exception, may alter in different app
     */
    protected final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";

    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    /**
     * 将其它线程的数据转发到UI线程
     */
    private Handler mDeliveryHandler;
    private DisposeDataListener mListener;
    private Class<?> mClass;
    private Type mType;//TypeToken

    public CommonJsonCallback(DisposeDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mType = handle.mType;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull final IOException e) {
        /**
         * 此时还在非UI线程，因此要转发
         */
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e));
            }
        });
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        int code = response.code();
        LogUtil.d(TAG, "code ---- >" + code);
        final String result = response.body().string();
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(String result) {
        if (result == null || result.trim().equals("")) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {
            //mClass和mType都为null则应用层需要自己解析
            if (mClass == null && mType == null) {
                mListener.onSuccess(result);
            } else {
                Object obj;
                if (mType != null) {
                    //使用TypeToken进行解析
                    obj = new Gson().fromJson(result, mType);
                } else {
                    //使用字节码进行解析
                    obj = new Gson().fromJson(result, mClass);
                }
                if (obj != null) {
                    mListener.onSuccess(obj);
                } else {
                    mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                }
            }
        } catch (Exception e) {
            mListener.onFailure(new OkHttpException(OTHER_ERROR, e.getMessage()));
            LogUtil.d(TAG, "Json onFailure -----> " + e.toString());
        }
    }
}
