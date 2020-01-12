package com.example.teachingblog.network.listener;

import com.example.teachingblog.models.Article;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author vision
 */
public class DisposeDataHandle {
    public DisposeDataListener mListener = null;
    public Class<?> mClass = null; //要解析成的对象
    public String mSource = null; //文件保存路径
    public Type mType = null;//TypeToken

    /**
     * 应用层需要自己解析
     *
     * @param listener
     */
    public DisposeDataHandle(DisposeDataListener listener) {
        this.mListener = listener;
    }

    /**
     * 使用TypeToken进行解析
     *
     * @param listener
     * @param type
     */
    public DisposeDataHandle(DisposeDataListener listener, Type type) {
        this.mListener = listener;
        this.mType = type;
    }

    /**
     * 使用字节码进行解析
     *
     * @param listener
     * @param clazz
     */
    public DisposeDataHandle(DisposeDataListener listener, Class<?> clazz) {
        this.mListener = listener;
        this.mClass = clazz;
    }

    public DisposeDataHandle(DisposeDataListener listener, String source) {
        this.mListener = listener;
        this.mSource = source;
    }
}