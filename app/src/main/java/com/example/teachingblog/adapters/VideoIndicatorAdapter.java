package com.example.teachingblog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.teachingblog.R;
import com.example.teachingblog.utils.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

public class VideoIndicatorAdapter extends CommonNavigatorAdapter {

    private final String[] mTitles;
    private OnIndicatorTapClickListener mOnTapClickListener;

    public VideoIndicatorAdapter(Context context) {
        mTitles = context.getResources().getStringArray(R.array.video_indicator_title);
    }

    @Override
    public int getCount() {
        if (mTitles != null) {
            return mTitles.length;
        }
        return 0;
    }

    /**
     * 获取UI界面
     *
     * @param context
     * @param index
     * @return
     */
    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        //创建view
        SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        //设置要显示的内容
        simplePagerTitleView.setText(mTitles[index]);
        //单位sp
        simplePagerTitleView.setTextSize(16);
        simplePagerTitleView.setNormalColor(Color.parseColor("#5D5D5D"));
        simplePagerTitleView.setSelectedColor(Color.parseColor("#757575"));
        //设置title的点击事件，这里的话，如果点击了title,那么就选中下面的viewPager到对应的index里面去
        //也就是说，当我们点击了title的时候，下面的viewPager会对应着index进行切换内容。
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTapClickListener != null) {
                    mOnTapClickListener.onTapClick(index);
                }
            }
        });
        //把这个创建好的view返回回去
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        indicator.setColors(context.getResources().getColor(R.color.colorPrimary));
        return indicator;
    }

    public void setOnIndicatorTapClickListener(OnIndicatorTapClickListener listener) {
        this.mOnTapClickListener = listener;
    }

    /**
     * 往外部暴露接口（在adapter内部点击事件的暴露）步骤：
     * 1、创建一个接口
     * 2、在接口里定义一个方法供外部去写
     * 3、定义一个设置监听器的方法
     * 4、在adapter内部点击事件调用接口方法
     */
    public interface OnIndicatorTapClickListener {
        void onTapClick(int index);
    }
}
