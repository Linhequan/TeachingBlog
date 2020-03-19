package com.example.teachingblog.models;

import com.google.gson.Gson;
import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class Article extends SimpleBannerInfo {
    /**
     * id : 3
     * title : CSS实现垂直居中的常用方法
     * body : 在前端开发过程中，盒子居中是常常用到的。其中 ，居中又可以分为水平居中和垂直居中。水平居中是比较容易的，直接设置元素的margin: 0 auto就可以实现。但是垂直居中相对来说是比较复杂一些的。下面我们一起来讨论一下实现垂直居中的方法。
     * <p>
     * 　　首先，定义一个需要垂直居中的div元素，他的宽度和高度均为300px，背景色为橙色。代码如下：
     * addtime : 2019-09-01T04:35:56.000Z
     * type : CSS
     * author : 林三心
     * img_path : http://47.100.137.31/img/css2.png
     * md_path : /uploads/articles/20200307/aeea811b8529441894c27be7398d1ab4.md
     * is_delete : 0
     */

    private int id;
    private String title;
    private String body;
    private String addtime;
    private String type;
    private String author;
    private String img_path;
    private String md_path;
    private int is_delete;

    public static Article objectFromData(String str) {
        return new Gson().fromJson(str, Article.class);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getMd_path() {
        return md_path;
    }

    public void setMd_path(String md_path) {
        this.md_path = md_path;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    //获取加载图片的值
    @Override
    public Object getXBannerUrl() {
        return getImg_path();
    }

    //获取图片的标题
    @Override
    public String getXBannerTitle() {
        return getTitle();
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", addtime='" + addtime + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", img_path='" + img_path + '\'' +
                ", md_path='" + md_path + '\'' +
                ", is_delete=" + is_delete +
                '}';
    }
}
