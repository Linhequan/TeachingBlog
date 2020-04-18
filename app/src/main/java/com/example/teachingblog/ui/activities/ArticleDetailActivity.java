package com.example.teachingblog.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teachingblog.R;
import com.example.teachingblog.base.BaseActivity;
import com.example.teachingblog.interfaces.IArticleDetailViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.presenters.ArticleDetailPresenter;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;
import com.example.teachingblog.views.UILoader;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.DrawableGetter;
import com.zzhoujay.richtext.callback.OnUrlClickListener;

import java.text.ParseException;

public class ArticleDetailActivity extends BaseActivity implements IArticleDetailViewCallback, UILoader.OnRetryClickListener {

    private static final String TAG = "ArticleDetailActivity";
    private ArticleDetailPresenter mArticleDetailPresenter;
    private TextView mDetailArticleTitle;
    private TextView mDetailArticleAuthor;
    private TextView mDetailArticleAddTime;
    private FrameLayout mArticleDetailContainer;
    private UILoader mUiLoader;
    private static boolean isFirstEnter = true;

    private int mCurrentId = -1;
    private String mCurrentTitle = "";
    private ImageView mTitleBarBackIv;
    private ImageView mTitleBarMoreIv;
    private TextView mTitleBarTypeTv;
    private TextView mRichTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        //设置透明状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        //是否是第一次进入
        if (isFirstEnter) {
            isFirstEnter = false;
            // 设置缓存目录，若不设置将不会对图片进行本地缓存
            RichText.initCacheDir(this);
            //开启debug模式
            RichText.debugMode = true;
        }

        initView();
        initListener();

        mArticleDetailPresenter = ArticleDetailPresenter.getInstance();
        mArticleDetailPresenter.registerViewCallback(this);

    }

    private void initListener() {

        //返回按钮被点击了
        mTitleBarBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //更多按钮被点击了
        mTitleBarMoreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentId != -1 && !mCurrentTitle.equals("")) {
                    String shareArticleText = Utils.getShareArticleText(mCurrentTitle, mCurrentId);
                    Utils.shareText(ArticleDetailActivity.this, shareArticleText, "Share");
                }
            }
        });
    }

    private void initView() {
        mArticleDetailContainer = this.findViewById(R.id.article_detail_container);
        mTitleBarBackIv = this.findViewById(R.id.title_bar_back_iv);
        mTitleBarMoreIv = this.findViewById(R.id.title_bar_more_iv);
        mTitleBarTypeTv = this.findViewById(R.id.title_bar_type_tv);

        if (mUiLoader == null) {
            mUiLoader = new UILoader(this) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };
            mArticleDetailContainer.removeAllViews();
            mArticleDetailContainer.addView(mUiLoader);
            mUiLoader.setOnRetryClickListener(this);
        }
    }

    private View createSuccessView(ViewGroup container) {
        View articleDetailView = LayoutInflater.from(this).inflate(R.layout.item_article_detail, container, false);
        mDetailArticleTitle = articleDetailView.findViewById(R.id.detail_article_title);
        mDetailArticleAuthor = articleDetailView.findViewById(R.id.detail_article_author);
        mDetailArticleAddTime = articleDetailView.findViewById(R.id.detail_article_addTime);

        mRichTv = articleDetailView.findViewById(R.id.rich_tv);

        return articleDetailView;
    }

    @Override
    public void onArticleLoaded(Article article) {
        LogUtil.d(TAG, "article ===== " + article);
        int id = article.getId();
        //保存当前id和title
        mCurrentId = id;
        mCurrentTitle = article.getTitle();
        //获取文章的详情内容
        if (mArticleDetailPresenter != null) {
            mArticleDetailPresenter.getArticleDetail(id);
        }

        //先加载titleBar的文章类型
        if (mTitleBarTypeTv != null) {
            mTitleBarTypeTv.setText(article.getType());
        }
        //文章标题
        if (mDetailArticleTitle != null) {
            mDetailArticleTitle.setText(article.getTitle());
        }
        //文章作者
        if (mDetailArticleAuthor != null) {
            mDetailArticleAuthor.setText(article.getAuthor());
        }
        String addTimeText = null;
        try {
            addTimeText = Utils.getDateFormat(article.getAddtime());
        } catch (ParseException e) {
            LogUtil.d(TAG, "ParseException --- > " + e.toString());
        }
        if (addTimeText != null && mDetailArticleAddTime != null) {
            mDetailArticleAddTime.setText(addTimeText);
        }
    }

    @Override
    public void onArticleDetailLoaded(String result) {

//        String data = Html.fromHtml(result).toString();
        //替换img属性
//        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        result = result.replace("color:#010101", "color:#969696");
        result = result.replace("color:rgb(51,51,51)", "color:#969696");
        result = result.replace("color:rgb(47,47,47)", "color:#969696");
        if (mRichTv != null) {
            RichText.from(result)
                    .autoFix(true)//是否自动修复，默认true
                    .placeHolder(new DrawableGetter() {
                        @Override
                        public Drawable getDrawable(ImageHolder holder, RichTextConfig config, TextView textView) {
                            return ArticleDetailActivity.this.getResources().getDrawable(R.mipmap.default_image);
                        }
                    })//设置加载中显示的占位图
                    .errorImage(new DrawableGetter() {
                        @Override
                        public Drawable getDrawable(ImageHolder holder, RichTextConfig config, TextView textView) {
                            return ArticleDetailActivity.this.getResources().getDrawable(R.mipmap.default_image);
                        }
                    })// 设置加载失败的错误图
                    .urlClick(new OnUrlClickListener() {
                        @Override
                        public boolean urlClicked(String url) {
                            if (url.startsWith("#")) {
                                startBrowserView(url);
                                return true;
                            } else if (url.startsWith("code://")) {
                                startBrowserView(url);
                                return true;
                            }
                            return false;
                        }
                    })//设置链接点击回调
                    .into(mRichTv);//设置目标TextView
        }

        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
        }
    }

    /**
     * 跳转到浏览器页面
     *
     * @param url
     */
    private void startBrowserView(String url) {
        String articleUrl = Utils.getArticleAnchorUrl(url, mCurrentId);

        boolean isUri = Utils.isURI(articleUrl);
        Log.d(TAG, "startBrowserView ：isUri ==== " + isUri);
        if (isUri) {
            //如果手机本身安装了多个浏览器而又没有设置默认浏览器的话，
            //系统将让用户选择使用哪个浏览器来打开连接
            Uri uri = Uri.parse(articleUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    @Override
    public void onNetworkError() {
        //请求发生错误，显示网络异常状态
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
        }
    }

    @Override
    public void onLoading() {
        //显示Loading状态
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
        }
    }

    @Override
    public void onRetryClick() {
        //表示网络不佳的时候，用户点击了重试
        if (mArticleDetailPresenter != null) {
            mArticleDetailPresenter.getArticleDetail(mCurrentId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (mArticleDetailPresenter != null) {
            mArticleDetailPresenter.unRegisterViewCallback(this);
            mArticleDetailPresenter = null;
        }
    }
}
