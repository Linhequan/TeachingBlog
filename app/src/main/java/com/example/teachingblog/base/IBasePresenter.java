package com.example.teachingblog.base;

public interface IBasePresenter<T> {
    /**
     * 这个方法用于注册UI的回调
     *
     * @param t
     */
    void registerViewCallback(T t);

    /**
     * 这个方法用于取消UI的回调
     *
     * @param t
     */
    void unRegisterViewCallback(T t);
}
