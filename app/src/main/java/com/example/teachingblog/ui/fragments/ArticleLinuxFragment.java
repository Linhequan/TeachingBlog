package com.example.teachingblog.ui.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.teachingblog.R;
import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.interfaces.IArticleLinuxViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.presenters.ArticleDetailPresenter;
import com.example.teachingblog.presenters.ArticleLinuxPresenter;
import com.example.teachingblog.ui.activities.ArticleDetailActivity;
import com.example.teachingblog.ui.adapters.LinuxArticleListAdapter;
import com.example.teachingblog.ui.adapters.PHPArticleListAdapter;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.views.UILoader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class ArticleLinuxFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, LinuxArticleListAdapter.OnArticleItemClickListener, IArticleLinuxViewCallback, UILoader.OnRetryClickListener {

    private static final String TAG = "ArticleLinuxFragment";
    private UILoader mUiLoader;
    private View mRootView;
    private RefreshLayout mRefreshLayout;
    private MaterialHeader mMaterialHeader;
    private RecyclerView mArticleList;
    private LinuxArticleListAdapter mLinuxArticleListAdapter;
    private ArticleLinuxPresenter mArticleLinuxPresenter;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {

        //UI加载器
        mUiLoader = new UILoader(getContext()) {
            @Override
            protected View getSuccessView(ViewGroup container) {
                return createSuccessView(layoutInflater, container);
            }
        };

        //获取到逻辑层的对象
        mArticleLinuxPresenter = ArticleLinuxPresenter.getInstance();
        //先要设置通知接口的注册
        mArticleLinuxPresenter.registerViewCallback(this);
        //获取分类为linux的所有文章
        mArticleLinuxPresenter.getLinuxArticle();

        //与它的父类解绑
        if (mUiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader);
        }

        //设置网络不佳的时候，用户点击了重试
        mUiLoader.setOnRetryClickListener(this);

        return mUiLoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_linux_article, container, false);

        //上拉和下拉刷新框架
        mRefreshLayout = mRootView.findViewById(R.id.linux_article_refreshLayout);
        //Header
        mMaterialHeader = (MaterialHeader) mRefreshLayout.getRefreshHeader();
        //设置Header箭头颜色
        mMaterialHeader.setColorSchemeResources(R.color.colorPrimary);

        //单独设置刷新监听器
        mRefreshLayout.setOnRefreshListener(this);
        //单独设置加载监听器
        mRefreshLayout.setOnLoadMoreListener(this);

        //RecyclerView的使用步骤
        //1、找到控件
        mArticleList = mRootView.findViewById(R.id.linux_article_list);
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
        mLinuxArticleListAdapter = new LinuxArticleListAdapter();
        mArticleList.setAdapter(mLinuxArticleListAdapter);

        mLinuxArticleListAdapter.setOnArticleItemClickListener(this);

        return mRootView;
    }

    @Override
    public void onArticleListLoaded(List<Article> result, boolean noMoreData) {
        //当我们获取到内容的时候，这个方法就会被调用（成功了）
        //数据回来以后，就是更新UI了
        if (mLinuxArticleListAdapter != null) {
            mLinuxArticleListAdapter.setData(result);
        }
        if (noMoreData) {
            //没有更多数据（上拉加载功能将显示没有更多数据）
            mRefreshLayout.finishRefreshWithNoMoreData();
        }
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
    public void onLoaderMoreSuccess(List<Article> result, boolean noMoreData) {
        //进入这里说明加载数据成功了
        //数据回来以后，就是更新UI了
        if (mIsLoaderMore && mRefreshLayout != null && mLinuxArticleListAdapter != null) {
            mLinuxArticleListAdapter.setData(result);
            if (noMoreData) {
                //没有更多数据（上拉加载功能将显示没有更多数据）
                mRefreshLayout.finishLoadMoreWithNoMoreData();
                mIsLoaderMore = false;
            } else {
                //还有多的数据
                mRefreshLayout.finishLoadMore();
                mIsLoaderMore = false;
            }
        }
    }

    @Override
    public void onLoaderMoreFailure() {
        //进入这里说明加载数据失败了
        if (mIsLoaderMore && mRefreshLayout != null) {
            mRefreshLayout.finishLoadMore(false);
            mIsLoaderMore = false;
        }
    }

    @Override
    public void onRefreshSuccess(List<Article> result, boolean noMoreData) {
        //进入这里说明加载数据成功了
        //数据回来以后，就是更新UI了
        if (mIsRefresh && mRefreshLayout != null && mLinuxArticleListAdapter != null) {
            mLinuxArticleListAdapter.setData(result);
            if (noMoreData) {
                //没有更多数据（下拉刷新功能将显示没有更多数据）
                Toast.makeText(getContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                mRefreshLayout.finishRefreshWithNoMoreData();
                mIsRefresh = false;
            } else {
                //还有多的数据
                Toast.makeText(getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                mRefreshLayout.finishRefresh();
                mIsRefresh = false;
            }
        }
    }

    @Override
    public void onRefreshFailure() {
        //进入这里说明加载数据失败了
        if (mIsRefresh && mRefreshLayout != null) {
            Toast.makeText(getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
            mRefreshLayout.finishRefresh(false);
            mIsRefresh = false;
        }
    }

    //是否正在上拉加载更多
    private boolean mIsLoaderMore = false;
    //是否正在下拉刷新
    private boolean mIsRefresh = false;

    //单独设置刷新监听器
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //下拉刷新
        if (mArticleLinuxPresenter != null && !mIsRefresh) {
            mArticleLinuxPresenter.pull2RefreshMore();
            mIsRefresh = true;
        }
    }

    //单独设置加载监听器
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //上拉加载更多
        if (mArticleLinuxPresenter != null && !mIsLoaderMore) {
            mArticleLinuxPresenter.loadMore();
            mIsLoaderMore = true;
        }
    }

    @Override
    public void onRetryClick() {
        //表示网络不佳的时候，用户点击了重试
        //重新获取数据即可
        if (mArticleLinuxPresenter != null) {
            mArticleLinuxPresenter.getLinuxArticle();
        }
    }

    @Override
    public void onItemClick(Article article) {
        //item被点击了，跳转到详情界面
        //点击跳转详情页，并传相应的文章
        ArticleDetailPresenter.getInstance().setTargetArticle(article);
        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mArticleLinuxPresenter != null) {
            //取消接口的注册
            mArticleLinuxPresenter.unRegisterViewCallback(this);
        }
    }

}
