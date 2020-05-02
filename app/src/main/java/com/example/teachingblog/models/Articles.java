package com.example.teachingblog.models;

import java.util.List;

/**
 * 专门用于处理PHP和Linux分类文章的bean类
 */
public class Articles {

    /**
     * data : {"list":[{"id":73,"title":"ThinkPhp5下使用restful风格路由导致跨域put请求失效解决办法","body":"今天写代码的时候，碰到ajax发起put请求失败的问题，碰到这个情况的时候时候我也一脸懵逼，不过还好，费了不少功夫终于解决。","addtime":"2020-03-13 11:46:20","type":"PHP","author":"林三心","img_path":"http://47.100.137.31/img/php2.png","md_path":"/uploads/articles/20200313/f3101782e81d2b4c4caa7e9ad1b7fdba.md","is_delete":0},{"id":70,"title":"PHPFatalerror: Uncaught think\\\\exception\\\\ErrorException相关解决方法","body":"解决方法：切换到tp5目录然后使用chmod -R 777 tp5来解决，当然不太建议这样做，这不符合我们在线上的操作方式。一种比较推荐的做法是执行chmod -R daemon:daemon tp5来进行处理，这是因为在Mac上默认的Apache用户为daemon，当然你也可以通过在配置文件中修改用户名和所属组来解决这一问题。\n","addtime":"2020-03-13 11:14:17","type":"PHP","author":"林三心","img_path":"http://47.100.137.31/img/php2.png","md_path":"/uploads/articles/20200313/4fd1d5b4b9998bb5865b6004a68d8ede.md","is_delete":0}],"total":{"tec":48,"life":6}}
     * meta : {"msg":"查询成功","status":200}
     */

    private DataBean data;
    private MetaBean meta;

    public static Articles objectFromData(String str) {

        return new com.google.gson.Gson().fromJson(str, Articles.class);
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public static class DataBean {
        /**
         * list : [{"id":73,"title":"ThinkPhp5下使用restful风格路由导致跨域put请求失效解决办法","body":"今天写代码的时候，碰到ajax发起put请求失败的问题，碰到这个情况的时候时候我也一脸懵逼，不过还好，费了不少功夫终于解决。","addtime":"2020-03-13 11:46:20","type":"PHP","author":"林三心","img_path":"http://47.100.137.31/img/php2.png","md_path":"/uploads/articles/20200313/f3101782e81d2b4c4caa7e9ad1b7fdba.md","is_delete":0},{"id":70,"title":"PHPFatalerror: Uncaught think\\\\exception\\\\ErrorException相关解决方法","body":"解决方法：切换到tp5目录然后使用chmod -R 777 tp5来解决，当然不太建议这样做，这不符合我们在线上的操作方式。一种比较推荐的做法是执行chmod -R daemon:daemon tp5来进行处理，这是因为在Mac上默认的Apache用户为daemon，当然你也可以通过在配置文件中修改用户名和所属组来解决这一问题。\n","addtime":"2020-03-13 11:14:17","type":"PHP","author":"林三心","img_path":"http://47.100.137.31/img/php2.png","md_path":"/uploads/articles/20200313/4fd1d5b4b9998bb5865b6004a68d8ede.md","is_delete":0}]
         * total : {"tec":48,"life":6}
         */

        private TotalBean total;
        private List<Article> list;

        public static DataBean objectFromData(String str) {

            return new com.google.gson.Gson().fromJson(str, DataBean.class);
        }

        public TotalBean getTotal() {
            return total;
        }

        public void setTotal(TotalBean total) {
            this.total = total;
        }

        public List<Article> getList() {
            return list;
        }

        public void setList(List<Article> list) {
            this.list = list;
        }

        public static class TotalBean {
            /**
             * tec : 48
             * life : 6
             */

            private int tec;
            private int life;

            public static TotalBean objectFromData(String str) {

                return new com.google.gson.Gson().fromJson(str, TotalBean.class);
            }

            public int getTec() {
                return tec;
            }

            public void setTec(int tec) {
                this.tec = tec;
            }

            public int getLife() {
                return life;
            }

            public void setLife(int life) {
                this.life = life;
            }

            @Override
            public String toString() {
                return "TotalBean{" +
                        "tec=" + tec +
                        ", life=" + life +
                        '}';
            }
        }

//        public static class ListBean {
//            /**
//             * id : 73
//             * title : ThinkPhp5下使用restful风格路由导致跨域put请求失效解决办法
//             * body : 今天写代码的时候，碰到ajax发起put请求失败的问题，碰到这个情况的时候时候我也一脸懵逼，不过还好，费了不少功夫终于解决。
//             * addtime : 2020-03-13 11:46:20
//             * type : PHP
//             * author : 林三心
//             * img_path : http://47.100.137.31/img/php2.png
//             * md_path : /uploads/articles/20200313/f3101782e81d2b4c4caa7e9ad1b7fdba.md
//             * is_delete : 0
//             */
//
//            private int id;
//            private String title;
//            private String body;
//            private String addtime;
//            private String type;
//            private String author;
//            private String img_path;
//            private String md_path;
//            private int is_delete;
//
//            public static ListBean objectFromData(String str) {
//
//                return new com.google.gson.Gson().fromJson(str, ListBean.class);
//            }
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getTitle() {
//                return title;
//            }
//
//            public void setTitle(String title) {
//                this.title = title;
//            }
//
//            public String getBody() {
//                return body;
//            }
//
//            public void setBody(String body) {
//                this.body = body;
//            }
//
//            public String getAddtime() {
//                return addtime;
//            }
//
//            public void setAddtime(String addtime) {
//                this.addtime = addtime;
//            }
//
//            public String getType() {
//                return type;
//            }
//
//            public void setType(String type) {
//                this.type = type;
//            }
//
//            public String getAuthor() {
//                return author;
//            }
//
//            public void setAuthor(String author) {
//                this.author = author;
//            }
//
//            public String getImg_path() {
//                return img_path;
//            }
//
//            public void setImg_path(String img_path) {
//                this.img_path = img_path;
//            }
//
//            public String getMd_path() {
//                return md_path;
//            }
//
//            public void setMd_path(String md_path) {
//                this.md_path = md_path;
//            }
//
//            public int getIs_delete() {
//                return is_delete;
//            }
//
//            public void setIs_delete(int is_delete) {
//                this.is_delete = is_delete;
//            }
//
//            @Override
//            public String toString() {
//                return "ListBean{" +
//                        "id=" + id +
//                        ", title='" + title + '\'' +
//                        ", body='" + body + '\'' +
//                        ", addtime='" + addtime + '\'' +
//                        ", type='" + type + '\'' +
//                        ", author='" + author + '\'' +
//                        ", img_path='" + img_path + '\'' +
//                        ", md_path='" + md_path + '\'' +
//                        ", is_delete=" + is_delete +
//                        '}';
//            }
//        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "total=" + total +
                    ", list=" + list +
                    '}';
        }
    }

    public static class MetaBean {
        /**
         * msg : 查询成功
         * status : 200
         */

        private String msg;
        private int status;

        public static MetaBean objectFromData(String str) {

            return new com.google.gson.Gson().fromJson(str, MetaBean.class);
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "MetaBean{" +
                    "msg='" + msg + '\'' +
                    ", status=" + status +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Articles{" +
                "data=" + data +
                ", meta=" + meta +
                '}';
    }
}
