package com.example.teachingblog.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teachingblog.R;
import com.example.teachingblog.adapters.IndicatorAdapter;
import com.example.teachingblog.adapters.MainContentAdapter;
import com.example.teachingblog.base.BaseFragment;
import com.example.teachingblog.utils.LogUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class ClassificationFragment extends BaseFragment {

    private static final String TAG = "ClassificationFragment";

    private MagicIndicator mMagicIndicator;
    private ViewPager mContentPager;
    private IndicatorAdapter mIndicatorAdapter;

    @Override
    protected View onSubViewLoaded(LayoutInflater layoutInflater, ViewGroup container) {
        View view = layoutInflater.inflate(R.layout.fragment_classification, container, false);
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
    private void initMagicIndicator(ViewGroup container, View view) {
        mMagicIndicator = view.findViewById(R.id.main_indicator);
        mMagicIndicator.setBackgroundColor(container.getContext().getResources().getColor(R.color.NavigationBg));
        //创建indicator的适配器
        mIndicatorAdapter = new IndicatorAdapter(container.getContext());
        CommonNavigator commonNavigator = new CommonNavigator(container.getContext());
        //设置Tap栏自我调节
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(mIndicatorAdapter);

        //ViewPager
        mContentPager = view.findViewById(R.id.content_pager);

        //创建内容适配器
        FragmentManager fragmentManager = getFragmentManager();
        MainContentAdapter mainContentAdapter = new MainContentAdapter(fragmentManager);
        mContentPager.setAdapter(mainContentAdapter);

        //把ViewPager和indicator绑定到一起
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mContentPager);
    }

    private void initEvent() {
        mIndicatorAdapter.setOnIndicatorTapClickListener(new IndicatorAdapter.OnIndicatorTapClickListener() {
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
