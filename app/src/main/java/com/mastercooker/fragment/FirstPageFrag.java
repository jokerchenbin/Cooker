package com.mastercooker.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercooker.R;
import com.mastercooker.adapter.FoodStyleListFragmentAdapter;
import com.mastercooker.model.FoodStyle;
import com.mastercooker.model.Util;

import java.util.ArrayList;


public class FirstPageFrag extends Fragment {

    public FirstPageFrag() {
        super();
    }

    public static FirstPageFrag newInstance() {
        return new FirstPageFrag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_page_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.first_page_frag_tl);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.first_page_frag_vp);

        ArrayList<FoodStyle> foodStyles = new ArrayList<>();
        for(int i = 1,j= 1;i<1500;i+=100,j++){
            int id = getResources().getIdentifier(Util.getZero(i),"drawable","com.mastercooker");
            FoodStyle foodStyle = new FoodStyle(id,"美食("+j+")",i);
            foodStyles.add(foodStyle);
        }

        FoodStyleListFragment foodStyleListFragmentFirst = FoodStyleListFragment.newInstance(foodStyles);

        ArrayList<FoodStyle> styleArrayList = new ArrayList<>();
        for(int i = 1501,j= 1;i<3000;i+=100,j++){
            int id = getResources().getIdentifier(Util.getZero(i),"drawable","com.mastercooker");
            FoodStyle foodStyle = new FoodStyle(id,"美食("+j+")",i);
            styleArrayList.add(foodStyle);
        }

        FoodStyleListFragment foodStyleListFragmentLast = FoodStyleListFragment.newInstance(styleArrayList);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(foodStyleListFragmentFirst);
        fragments.add(foodStyleListFragmentLast);

        FoodStyleListFragmentAdapter foodStyleListFragmentAdapter =
                new FoodStyleListFragmentAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(foodStyleListFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
