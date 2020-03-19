package com.example.teachingblog.fragments;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.teachingblog.R;
import com.example.teachingblog.adapters.VideoIndicatorAdapter;
import com.example.teachingblog.adapters.VideoMainContentAdapter;
import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class VideoFragment extends BaseFragment {

    private static final String TAG = "VideoFragment";
    private MagicIndicator mMagicIndicator;
    private VideoIndicatorAdapter mVideoIndicatorAdapter;
    private ViewPager mContentPager;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.fragment_video, container, false);
        //初始化指示器
        initMagicIndicator(container, view);
        initEvent();
        return view;
    }

    /**
     * 初始化指示器
     *
     * @param container
     * @param view
     */
    private void initMagicIndicator(final ViewGroup container, View view) {
        mMagicIndicator = view.findViewById(R.id.video_main_indicator);
        mMagicIndicator.setBackgroundColor(container.getContext().getResources().getColor(R.color.MagicIndicatorBg));
        //创建indicator的适配器
        mVideoIndicatorAdapter = new VideoIndicatorAdapter(container.getContext());
        CommonNavigator commonNavigator = new CommonNavigator(container.getContext());
        //设置Tap栏自我调节
//        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mVideoIndicatorAdapter);

        //ViewPager
        mContentPager = view.findViewById(R.id.video_content_pager);

        //创建内容适配器
        FragmentManager fragmentManager = getFragmentManager();
        VideoMainContentAdapter videoMainContentAdapter = new VideoMainContentAdapter(fragmentManager);
        mContentPager.setAdapter(videoMainContentAdapter);

        //把ViewPager和indicator绑定到一起
        mMagicIndicator.setNavigator(commonNavigator);

        //给外层的LinearLayout设置divider以设置title的间距
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(container.getContext(), 33);
            }
        });

        ViewPagerHelper.bind(mMagicIndicator, mContentPager);
    }

    private void initEvent() {
        mVideoIndicatorAdapter.setOnIndicatorTapClickListener(new VideoIndicatorAdapter.OnIndicatorTapClickListener() {
            @Override
            public void onTapClick(int index) {
                LogUtil.d(TAG, "click index is ---> " + index);
                if (mContentPager != null) {
                    mContentPager.setCurrentItem(index);
                }
            }
        });
    }
}
