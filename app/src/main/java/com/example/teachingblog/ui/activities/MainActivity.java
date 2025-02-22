package com.example.teachingblog.ui.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.example.teachingblog.R;
import com.example.teachingblog.base.BaseActivity;
import com.example.teachingblog.ui.fragments.ClassificationFragment;
import com.example.teachingblog.ui.fragments.HomeFragment;
import com.example.teachingblog.ui.fragments.VideoFragment;
import com.next.easynavigation.view.EasyNavigationBar;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    //导航栏高度
    public static final int NAVIGATION_HEIGHT = 50;
    private String[] mTabText;
    //未选中icon
    private int[] mNormalIcon = {R.mipmap.home_normal, R.mipmap.classification_normal, R.mipmap.video_normal};
    //选中时icon
    private int[] mSelectIcon = {R.mipmap.home_select, R.mipmap.classification_select, R.mipmap.video_select};
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        EasyNavigationBar navigationBar = findViewById(R.id.navigationBar);

        mTabText = this.getResources().getStringArray(R.array.navigation_title);
        fragments.add(new HomeFragment());
        fragments.add(new ClassificationFragment());
        fragments.add(new VideoFragment());

        navigationBar.titleItems(mTabText)
                .normalIconItems(mNormalIcon)
                .selectIconItems(mSelectIcon)
                .fragmentList(fragments)
                .normalTextColor(this.getResources().getColor(R.color.normalTextColor))
                .selectTextColor(this.getResources().getColor(R.color.selectTextColor))
                .navigationBackground(this.getResources().getColor(R.color.NavigationBg))
                .navigationHeight(NAVIGATION_HEIGHT)
                .lineColor(this.getResources().getColor(R.color.lineBg))
                .canScroll(true)
                .fragmentManager(getSupportFragmentManager())
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在应用退出时调用
        RichText.recycle();
    }
}
