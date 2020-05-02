package com.example.teachingblog.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.teachingblog.R;
import com.example.teachingblog.ui.adapters.VideoListAdapter;
import com.example.teachingblog.base.BaseActivity;
import com.example.teachingblog.utils.customMedia.JZMediaIjk;
import com.example.teachingblog.interfaces.IVideoDetailViewCallback;
import com.example.teachingblog.models.Video;
import com.example.teachingblog.presenters.VideoDetailPresenter;
import com.example.teachingblog.utils.LogUtil;
import com.example.teachingblog.utils.Utils;

import java.text.ParseException;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoDetailActivity extends BaseActivity implements IVideoDetailViewCallback, VideoListAdapter.OnDetailVideoItemClickListener {

    private static final String TAG = "VideoDetailActivity";
    private VideoDetailPresenter mVideoDetailPresenter;
    private JzvdStd mJzvdStd;
    private TextView mDetailVideoTitle;
    private TextView mDetailVideoAuthor;
    private TextView mDetailVideoAddTime;
    private RecyclerView mVideoList;
    private VideoListAdapter mVideoListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
//        View.SYSTEM_UI_FLAG_LAYOUT_STABLE：全屏显示时保证尺寸不变。
//        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，状态栏显示在Activity页面上面。
//        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏导航栏
//        View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
//        View.SYSTEM_UI_FLAG_VISIBLE：Activity非全屏显示，显示状态栏和导航栏。
//        View.INVISIBLE：Activity伸展全屏显示，隐藏状态栏。
//        View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY：必须配合View.SYSTEM_UI_FLAG_FULLSCREEN和View.SYSTEM_UI_FLAG_HIDE_NAVIGATION组合使用，达到的效果是拉出状态栏和导航栏后显示一会儿消失。

        //设置刚进入该页面时Activity全屏显示，且状态栏被隐藏覆盖掉；拉出状态栏后显示在Activity页面上面,并且不消失
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
        //设置透明状态栏
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        initView();

        mVideoDetailPresenter = VideoDetailPresenter.getInstance();
        mVideoDetailPresenter.registerViewCallback(this);
    }

    private void initView() {
        mJzvdStd = this.findViewById(R.id.jz_video);
        mDetailVideoTitle = this.findViewById(R.id.detail_video_title);
        mDetailVideoAuthor = this.findViewById(R.id.detail_video_author);
        mDetailVideoAddTime = this.findViewById(R.id.detail_video_addTime);

        //RecyclerView的使用步骤
        //1、找到控件
        mVideoList = this.findViewById(R.id.video_list);
        //2、设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mVideoList.setLayoutManager(linearLayoutManager);
        //设置卡片的边距
        mVideoList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = 1;
            }
        });
        //3、设置适配器
        mVideoListAdapter = new VideoListAdapter();
        mVideoList.setAdapter(mVideoListAdapter);

        mVideoListAdapter.setOnDetailVideoItemClickListener(this);
    }

    @Override
    public void onVideoLoaded(Video video, List<Video> videos) {
        LogUtil.d(TAG, "video ====== " + video + " video List ===== " + videos);

        //加载视频
        if (mJzvdStd != null) {

            mJzvdStd.setUp(video.getVideosrc(), video.getTitle(), JzvdStd.SCREEN_NORMAL, JZMediaIjk.class);
            Glide.with(this).load(video.getImgsrc()).into(mJzvdStd.thumbImageView);
        }
        //视频标题
        if (mDetailVideoTitle != null) {
            mDetailVideoTitle.setText(video.getTitle());
        }
        //视频作者
        if (mDetailVideoAuthor != null) {
            mDetailVideoAuthor.setText(video.getAuthor());
        }
        //视频发布时间
        String addTimeText = null;
        try {
            addTimeText = Utils.getDateFormat(video.getAddtime());
        } catch (ParseException e) {
            LogUtil.d(TAG, "ParseException --- > " + e.toString());
        }
        if (addTimeText != null && mDetailVideoAddTime != null) {
            mDetailVideoAddTime.setText(addTimeText);
        }

        //设置RecyclerView的数据
        if (mVideoListAdapter != null) {
            mVideoListAdapter.setData(videos);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //home back
        JzvdStd.goOnPlayOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //     Jzvd.clearSavedProgress(this, null);
        //home back
        JzvdStd.goOnPlayOnPause();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClick(Video video, List<Video> videoList) {
        //item被点击了，跳转到详情界面
        //点击跳转详情页，并传相应的视频
        VideoDetailPresenter.getInstance().setTargetVideo(video, videoList);
        Intent intent = new Intent(this, VideoDetailActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (mVideoDetailPresenter != null) {
            mVideoDetailPresenter.unRegisterViewCallback(this);
            mVideoDetailPresenter = null;
            Jzvd.releaseAllVideos();
        }
    }
}
