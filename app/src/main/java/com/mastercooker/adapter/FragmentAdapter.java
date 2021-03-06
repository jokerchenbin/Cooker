package com.mastercooker.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> listfraFragments = null;

    public FragmentAdapter(FragmentManager fm, List<Fragment> listfraFragments) {
        super(fm);
        this.listfraFragments = listfraFragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position < listfraFragments.size()) {
            fragment = listfraFragments.get(position);
        } else {
            fragment = listfraFragments.get(0);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return listfraFragments.size();
    }
}
