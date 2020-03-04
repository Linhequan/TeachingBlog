package com.example.teachingblog.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachingblog.DetailActivity;
import com.example.teachingblog.R;
import com.example.teachingblog.adapters.HtmlArticleListAdapter;
import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.interfaces.IHtmlViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.presenters.ArticleDetailPresenter;
import com.example.teachingblog.presenters.HtmlPresenter;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.views.UILoader;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class ArticleHtmlFragment extends BaseFragment implements IHtmlViewCallback, HtmlArticleListAdapter.OnArticleItemClickListener, UILoader.OnRetryClickListener {

    private static final String TAG = "ArticleHtmlFragment";
    private HtmlPresenter mHtmlPresenter;
    private UILoader mUiLoader;
    private View mRootView;
    private RecyclerView mArticleList;
    private HtmlArticleListAdapter mHtmlArticleListAdapter;

    @Override
    protected View onSubViewLoaded(final LayoutInflater layoutInflater, ViewGroup container) {

        //UI加载器
        mUiLoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater, container);
            }
        };

        //获取到逻辑层的对象
        mHtmlPresenter = HtmlPresenter.getInstance();
        //先要设置通知接口的注册
        mHtmlPresenter.registerViewCallback(this);
        //获取分类为html的所有文章
        mHtmlPresenter.getHtmlArticle();

        //与它的父类解绑
        if (mUiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader);
        }

        //设置网络不佳的时候，用户点击了重试
        mUiLoader.setOnRetryClickListener(this);

        return mUiLoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_html_article, container, false);
        //RecyclerView的使用步骤
        //1、找到控件
        mArticleList = mRootView.findViewById(R.id.html_article_list);
        //2、设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mArticleList.setLayoutManager(linearLayoutManager);
        //设置卡片的边距
        mArticleList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = UIUtil.dip2px(view.getContext(), 5);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 5);
                outRect.left = UIUtil.dip2px(view.getContext(), 5);
                outRect.right = UIUtil.dip2px(view.getContext(), 5);
            }
        });
        //3、设置适配器
        mHtmlArticleListAdapter = new HtmlArticleListAdapter();
        mArticleList.setAdapter(mHtmlArticleListAdapter);

        mHtmlArticleListAdapter.setOnArticleItemClickListener(this);

        return mRootView;
    }

    @Override
    public void onArticleListLoaded(List<Article> result) {
        //当我们获取到内容的时候，这个方法就会被调用（成功了）
        //数据回来以后，就是更新UI了
        mHtmlArticleListAdapter.setData(result);
        mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
    }

    @Override
    public void onNetworkError() {
        LogUtil.d(TAG, "onNetworkError");
        mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
    }

    @Override
    public void onEmpty() {
        LogUtil.d(TAG, "onEmpty");
        mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
    }

    @Override
    public void onLoading() {
        LogUtil.d(TAG, "onLoading");
        mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
    }

    @Override
    public void onRetryClick() {
        //表示网络不佳的时候，用户点击了重试
        //重新获取数据即可
        if (mHtmlPresenter != null) {
            mHtmlPresenter.getHtmlArticle();
        }
    }

    @Override
    public void onItemClick(Article article) {
        //item被点击了，跳转到详情界面
        //点击跳转详情页，并传相应的文章
        ArticleDetailPresenter.getInstance().setTargetArticle(article);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mHtmlPresenter != null) {
            //取消接口的注册
            mHtmlPresenter.unRegisterViewCallback(this);
        }
    }
}
