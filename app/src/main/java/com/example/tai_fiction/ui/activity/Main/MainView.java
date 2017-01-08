package com.example.tai_fiction.ui.activity.main;

import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;

import com.example.tai_fiction.base.BaseView;

/**
 * Created by JinBao on 2017/1/7.
 */

public interface MainView extends BaseView {
     void setupDrawerContent(NavigationView navigationView);

    void setViewPager(ViewPager viewPager);
}
