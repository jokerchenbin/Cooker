package com.mastercooker.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class FoodStyleListFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;

    public FoodStyleListFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "男神美食";
            case 1:
                return "女神美食";
        }
        return super.getPageTitle(position);
    }
}
