package com.example.teachingblog.utils;

import android.content.Context;
import android.content.Intent;

import com.example.teachingblog.models.Article;
import com.example.teachingblog.models.Video;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * 将2019-09-01T04:35:56.000Z UTC通用标准时转换成yyyy-MM-dd HH:mm:ss格式
     *
     * @param create_time 需要转换的字符串
     * @return
     * @throws ParseException
     */
    public static String getDateFormat(String create_time) throws ParseException {

        String format = "";
        if (create_time != null && create_time != "NULL" && create_time != "") {
            if (isDate(create_time)) {
                format = create_time;
            } else {
                //转换日期格式(将Mon Jun 18 2018 00:00:00 GMT+0800 (中国标准时间) 转换成yyyy-MM-dd)
                create_time = create_time.replace("Z", " UTC");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                Date d = sdf1.parse(create_time);//Mon Mar 06 00:00:00 CST 2017
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                format = sdf.format(d);//2019-12-22 19:22:31
            }
        }

        return format;
    }

    /**
     * 判断是否是日期格式和范围
     *
     * @param date
     * @return
     */
    private static boolean isDate(String date) {
        /**
         * 判断日期格式和范围
         */
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();
        return dateType;
    }

    /**
     * 文章List手动分页
     *
     * @param page     当前页数
     * @param pageSize 每页得大小
     * @param list     分页的对象
     * @return
     */
    public static List<Article> getArticleListPage(int page, int pageSize, List<Article> list) {
        if (list == null || list.size() == 0) {
//            throw new RuntimeException("分页数据不能为空!");
            //直接返回对应的List
            return list;
        }

        int totalCount = list.size();
        page = page - 1;
        int fromIndex = page * pageSize;
        //分页不能大于总数
        if (fromIndex >= totalCount) {
//            throw new RuntimeException("页数或分页大小不正确!");
            //说明分类结果为0
            list.clear();
            return list;
        }
        int toIndex = ((page + 1) * pageSize);
        if (toIndex > totalCount) {
            toIndex = totalCount;
        }
        //返回此列表在指定的<tt> fromIndex </ tt>（包括）
        // 和<tt> toIndex </ tt>（不包括）之间的视图。
        return list.subList(fromIndex, toIndex);
    }

    /**
     * 视频List手动分页
     *
     * @param page     当前页数
     * @param pageSize 每页得大小
     * @param list     分页的对象
     * @return
     */
    public static List<Video> getVideoListPage(int page, int pageSize, List<Video> list) {
        if (list == null || list.size() == 0) {
//            throw new RuntimeException("分页数据不能为空!");
            //直接返回对应的List
            return list;
        }

        int totalCount = list.size();
        page = page - 1;
        int fromIndex = page * pageSize;
        //分页不能大于总数
        if (fromIndex >= totalCount) {
//            throw new RuntimeException("页数或分页大小不正确!");
            //说明分类结果为0
            list.clear();
            return list;
        }
        int toIndex = ((page + 1) * pageSize);
        if (toIndex > totalCount) {
            toIndex = totalCount;
        }
        //返回此列表在指定的<tt> fromIndex </ tt>（包括）
        // 和<tt> toIndex </ tt>（不包括）之间的视图。
        return list.subList(fromIndex, toIndex);
    }

    /**
     * 获取轮播图数据，也就是获取最新的5篇文章
     *
     * @param datas
     * @return
     */
    public static List<Article> getBannerDatas(List<Article> datas) {
        //也给Banner一份
        List<Article> bannerDatas = new ArrayList<>();
        if (datas.size() >= 5) {
            //取最新的5篇文章放入轮播图
            for (int i = datas.size() - 1; i >= datas.size() - 5; i--) {
                Article article = datas.get(i);
                bannerDatas.add(article);
            }
        }
        return bannerDatas;
    }

    /**
     * 获取文章锚点Url
     *
     * @param url
     * @param currentId
     * @return
     */
    public static String getArticleAnchorUrl(String url, int currentId) {

        String urlHeader = "http://47.100.137.31/article/index.html?id=" + currentId;
        if (url.startsWith("#")) {
            url = url.replaceFirst("#", "");
        } else if (url.startsWith("code://")) {
            url = url.replaceFirst("code://", "");
        }

        return urlHeader + url;
    }

    /**
     * 判断是否为URI格式
     * 这个方法需要经过实战检验，是网络上的
     * （1）验证http,https,ftp开头
     * <p>
     * （2）验证一个":"，验证多个"/"
     * <p>
     * （3）验证网址为 xxx.xxx
     * <p>
     * （4）验证有0个或1个问号
     * <p>
     * （5）验证参数必须为xxx=xxx格式，且xxx=空    格式通过
     * <p>
     * （6）验证参数与符号&连续个数为0个或1个
     *
     * @param str 字符串
     * @return true：是URI格式；false：否
     */
    public static boolean isURI(String str) {
        String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
        Pattern pat = Pattern.compile(regex.trim());//对比
        return pat.matcher(str.trim()).matches();//判断是否匹配
    }

    /**
     * 调用系统自带的分享功能，分享文本
     *
     * @param mContext
     * @param text
     * @param title
     */
    public static void shareText(Context mContext, String text, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        mContext.startActivity(Intent.createChooser(intent, title));
    }

    public static String getShareArticleText(String title, int currentId) {
        return title + "http://47.100.137.31/article/index.html?id=" + currentId;
    }

}
