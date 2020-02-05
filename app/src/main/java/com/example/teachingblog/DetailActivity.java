package com.example.teachingblog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.teachingblog.base.BaseActivity;
import com.example.teachingblog.interfaces.IArticleDetailViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.presenters.ArticleDetailPresenter;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;
import com.example.teachingblog.views.UILoader;

import java.text.ParseException;

public class DetailActivity extends BaseActivity implements IArticleDetailViewCallback, UILoader.OnRetryClickListener {

    private static final String TAG = "DetailActivity";
    private ArticleDetailPresenter mArticleDetailPresenter;
    private TextView mDetailArticleTitle;
    private TextView mDetailArticleAuthor;
    private TextView mDetailArticleAddTime;
    private FrameLayout mArticleDetailContainer;
    private UILoader mUiLoader;
    private WebView mWebView;

    private int mCurrentId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // TODO: 2020/1/28 0028 详情页完成，webView样式有空就调整
        //设置透明状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();

        mArticleDetailPresenter = ArticleDetailPresenter.getInstance();
        mArticleDetailPresenter.registerViewCallback(this);

    }

    private void initView() {
        mArticleDetailContainer = this.findViewById(R.id.article_detail_container);

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

        mWebView = articleDetailView.findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        return articleDetailView;
    }

    @Override
    public void onArticleLoaded(Article article) {
        LogUtil.d(TAG, "article ===== " + article);
        int id = article.getId();
        mCurrentId = id;
        //获取文章的详情内容
        if (mArticleDetailPresenter != null) {
            mArticleDetailPresenter.getArticleDetail(id);
        }

        if (mDetailArticleTitle != null) {
            mDetailArticleTitle.setText(article.getTitle());
        }
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
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, varjs + result, "text/html", "UTF-8", null);
        }
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
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
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (mArticleDetailPresenter != null) {
            mArticleDetailPresenter.unRegisterViewCallback(this);
            mArticleDetailPresenter = null;
        }
    }

    @Override
    public void onRetryClick() {
        //表示网络不佳的时候，用户点击了重试
        if (mArticleDetailPresenter != null) {
            mArticleDetailPresenter.getArticleDetail(mCurrentId);
        }
    }
}
