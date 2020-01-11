package com.example.teachingblog.models;

public class Article {

    /**
     * id : 3
     * title : CSS实现垂直居中的常用方法
     * body : 在前端开发过程中，盒子居中是常常用到的。其中 ，居中又可以分为水平居中和垂直居中。水平居中是比较容易的，直接设置元素的margin: 0 auto就可以实现。但是垂直居中相对来说是比较复杂一些的。下面我们一起来讨论一下实现垂直居中的方法。

     　　首先，定义一个需要垂直居中的div元素，他的宽度和高度均为300px，背景色为橙色。代码如下：
     * addtime : 2019-09-01T04:35:56.000Z
     * type : CSS
     * author : 林三心
     */

    private int id;
    private String title;
    private String body;
    private String addtime;
    private String type;
    private String author;

    public static Article objectFromData(String str) {

        return new com.google.gson.Gson().fromJson(str, Article.class);
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", addtime='" + addtime + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
