package com.example.teachingblog.fragments;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachingblog.R;
import com.example.teachingblog.adapters.HtmlCssVideoListAdapter;
import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.interfaces.IVideoHtmlCssViewCallback;
import com.example.teachingblog.models.Video;
import com.example.teachingblog.presenters.VideoHtmlCssPresenter;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.views.UILoader;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

public class VideoHtmlCssFragment extends BaseFragment implements IVideoHtmlCssViewCallback, UILoader.OnRetryClickListener {

    private static final String TAG = "VideoHtmlCssFragment";
    private UILoader mUiLoader;
    private View mRootView;
    private RecyclerView mVideoList;
    private HtmlCssVideoListAdapter mHtmlCssVideoListAdapter;
    private VideoHtmlCssPresenter mVideoHtmlCssPresenter;

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
        mVideoHtmlCssPresenter = VideoHtmlCssPresenter.getInstance();
        //先要设置通知接口的注册
        mVideoHtmlCssPresenter.registerViewCallback(this);
        //获取分类为Html/Css的所有视频
        mVideoHtmlCssPresenter.getHtmlCssVideo();

        //与它的父类解绑
        if (mUiLoader.getParent() instanceof ViewGroup) {
            ((ViewGroup) mUiLoader.getParent()).removeView(mUiLoader);
        }

        //设置网络不佳的时候，用户点击了重试
        mUiLoader.setOnRetryClickListener(this);

        return mUiLoader;
    }

    private View createSuccessView(LayoutInflater layoutInflater, ViewGroup container) {
        mRootView = layoutInflater.inflate(R.layout.fragment_html_css_video, container, false);
        //RecyclerView的使用步骤
        //1、找到控件
        mVideoList = mRootView.findViewById(R.id.html_css_video_list);
        //2、设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mVideoList.setLayoutManager(linearLayoutManager);
        //设置卡片的边距
        mVideoList.addItemDecoration(new RecyclerView.ItemDecoration() {
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
        mHtmlCssVideoListAdapter = new HtmlCssVideoListAdapter();
        mVideoList.setAdapter(mHtmlCssVideoListAdapter);

        // TODO: 2020/3/6 0006 完成item点击跳转

        return mRootView;
    }

    @Override
    public void onVideoListLoaded(List<Video> result) {
        //当我们获取到内容的时候，这个方法就会被调用（成功了）
        //数据回来以后，就是更新UI了
        mHtmlCssVideoListAdapter.setData(result);
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
        if (mVideoHtmlCssPresenter != null) {
            mVideoHtmlCssPresenter.getHtmlCssVideo();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mVideoHtmlCssPresenter != null) {
            //取消接口的注册
            mVideoHtmlCssPresenter.unRegisterViewCallback(this);
        }
    }
}
