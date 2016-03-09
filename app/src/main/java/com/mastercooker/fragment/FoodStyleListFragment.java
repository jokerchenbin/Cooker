package com.mastercooker.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercooker.FoodListActivity;
import com.mastercooker.R;
import com.mastercooker.adapter.FoodStyleAdapter;
import com.mastercooker.model.FoodStyle;

import java.util.ArrayList;

public class FoodStyleListFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public FoodStyleListFragment() {
        super();
    }

    public static FoodStyleListFragment newInstance(ArrayList<FoodStyle> foodStyles) {
        FoodStyleListFragment foodStyleListFragment = new FoodStyleListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("FoodStyles", foodStyles);
        foodStyleListFragment.setArguments(bundle);
        return foodStyleListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.food_style_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.food_style_list_rv);
        //获取瀑布流的列数
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int num = (int)(size.x/getResources().getDisplayMetrics().density/84);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(num-1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        FoodStyleAdapter foodStyleAdapter = new FoodStyleAdapter(getContext());

        Bundle bundle = getArguments();
        ArrayList<FoodStyle> foodStyles = bundle.getParcelableArrayList("FoodStyles");
        foodStyleAdapter.addAll(foodStyles);

        mRecyclerView.setAdapter(foodStyleAdapter);

        foodStyleAdapter.setOnItemClickListener(new FoodStyleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int imgId) {
                //跳转界面
                Intent intent = new Intent(getContext(), FoodListActivity.class);
                intent.putExtra("startId", imgId);
                startActivity(intent);
            }
        });
    }
}
