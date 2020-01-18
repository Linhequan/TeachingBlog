package com.example.teachingblog.fragments;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teachingblog.DetailActivity;
import com.example.teachingblog.R;
import com.example.teachingblog.adapters.ArticleListAdapter;
import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.interfaces.IHomeViewCallback;
import com.example.teachingblog.models.Article;
import com.example.teachingblog.presenters.HomePresenter;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.views.UILoader;
import com.stx.xhb.xbanner.XBanner;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment implements IHomeViewCallback, UILoader.OnRetryClickListener, ArticleListAdapter.OnArticleItemClickListener {

    private static final String TAG = "HomeFragment";
    private HomePresenter mHomePresenter;
    private RecyclerView mArticleList;
    private ArticleListAdapter mArticleListAdapter;
    private UILoader mUiLoader;
    private FrameLayout mArticleListContainer;
    private XBanner mArticleBanner;
    //banner的数据
    private List<Article> mBannerDatas = new ArrayList<>();

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.fragment_home, container, false);
        //文章列表容器
        mArticleListContainer = view.findViewById(R.id.article_list_container);
        //XBanner
        mArticleBanner = view.findViewById(R.id.xbanner);
        //初始化banner
        initBanner();

        //UI加载器
        if (mUiLoader == null) {
            mUiLoader = new UILoader(getContext()) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };
            mArticleListContainer.removeAllViews();
            mArticleListContainer.addView(mUiLoader);
            //设置网络不佳的时候，用户点击了重试
            mUiLoader.setOnRetryClickListener(this);
        }

        //获取到逻辑层的对象
        mHomePresenter = HomePresenter.getInstance();
        //先要设置通知接口的注册
        mHomePresenter.registerViewCallback(this);
        // 获取首页推荐文章
        mHomePresenter.getHomeRecommendArticle();

        return view;
    }

    private void initBanner() {
        //Banner条目点击事件
        mArticleBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Toast.makeText(getContext(), "点击了第" + position, Toast.LENGTH_SHORT).show();
                LogUtil.d(TAG, "banner article ===== " + mBannerDatas.get(position).toString());
                // TODO: 2020/1/18 0018 点击跳转详情页，并传相应的文章
                Intent intent = new Intent(getContext(), DetailActivity.class);
                startActivity(intent);
            }
        });
        //设置banner加载图片的方式
        mArticleBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Article article = (Article) model;
                int imgRes = (int) article.getXBannerUrl();
                ImageView imageView = (ImageView) view;
                imageView.setImageResource(imgRes);
            }
        });
        mArticleBanner.setBannerData(mBannerDatas);
    }

    private View createSuccessView(ViewGroup container) {
        View articleListView = LayoutInflater.from(getContext()).inflate(R.layout.item_article_list, container, false);
        //RecyclerView的使用步骤
        //1、找到控件
        mArticleList = articleListView.findViewById(R.id.article_list);
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
        mArticleListAdapter = new ArticleListAdapter();
        mArticleList.setAdapter(mArticleListAdapter);

        mArticleListAdapter.setOnArticleItemClickListener(this);

        return articleListView;
    }

    @Override
    public void onArticleListLoaded(List<Article> result) {
        //当我们获取到推荐内容的时候，这个方法就会被调用（成功了）
        //数据回来以后，就是更新UI了
        if (mArticleListAdapter != null) {
            mArticleListAdapter.setData(result);
        }
        //也给Banner一份
        //清除原来的数据
        mBannerDatas.clear();
        for (int i = result.size() - 1; i >= result.size() - 5; i--) {
            Article article = result.get(i);
            mBannerDatas.add(article);
        }
        if (mArticleBanner != null) {
            //刷新数据之后，需要重新设置是否支持自动轮播
            mArticleBanner.setAutoPlayAble(mBannerDatas.size() > 1);
            mArticleBanner.setBannerData(mBannerDatas);
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
    public void onRetryClick() {
        //表示网络不佳的时候，用户点击了重试
        //重新获取数据即可
        if (mHomePresenter != null) {
            mHomePresenter.getHomeRecommendArticle();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mHomePresenter != null) {
            //取消接口的注册
            mHomePresenter.unRegisterViewCallback(this);
        }
    }

    @Override
    public void onItemClick(Article article) {
        //item被点击了，跳转到详情界面
        // TODO: 2020/1/18 0018 点击跳转详情页，并传相应的文章
        LogUtil.d(TAG, "article ==== " + article.toString());
    }
}
