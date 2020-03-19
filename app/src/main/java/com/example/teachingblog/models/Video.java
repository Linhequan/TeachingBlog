package com.example.teachingblog.models;

import com.google.gson.Gson;

public class Video {
    /**
     * id : 1
     * title : 网页为什么叫HTML
     * addtime : 2019-10-10T05:31:17.000Z
     * imgsrc : http://47.100.137.31/img/videoImg/1.png
     * videosrc : http://47.100.137.31/video/1.mp4
     * type : HTML/CSS
     * author : 林三心搬运工
     * is_delete : 0
     */

    private int id;
    private String title;
    private String addtime;
    private String imgsrc;
    private String videosrc;
    private String type;
    private String author;
    private int is_delete;

    public static Video objectFromData(String str) {

        return new Gson().fromJson(str, Video.class);
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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getVideosrc() {
        return videosrc;
    }

    public void setVideosrc(String videosrc) {
        this.videosrc = videosrc;
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

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", addtime='" + addtime + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", videosrc='" + videosrc + '\'' +
                ", type='" + type + '\'' +
                ", author='" + author + '\'' +
                ", is_delete=" + is_delete +
                '}';
    }
}
