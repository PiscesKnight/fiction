package com.example.tai_fiction.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 泰子 on 2015/10/17.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mFragemntTitles = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    public void addFragemnt(Fragment fragments,String titles){
        mFragemntTitles.add(titles);
        mFragments.add(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragemntTitles.get(position);
    }


}
