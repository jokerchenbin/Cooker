package com.mastercooker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mastercooker.R;
import com.mastercooker.activity.ModifyPswActivity;
import com.mastercooker.activity.MyCollectionActivity;
import com.mastercooker.activity.MyFoodActivity;
import com.mastercooker.activity.MyPostActivity;
import com.mastercooker.model.MyUser;

import cn.bmob.v3.BmobUser;

public class FourthPageFrag extends Fragment implements View.OnClickListener {
    private LinearLayout layout1,layout2;
    private TextView textView;
    private View view;

    public static FourthPageFrag newInstance() {
        return new FourthPageFrag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.four_page_frag, container, false);
        initView();
        MyUser myUser = BmobUser.getCurrentUser(getContext(), MyUser.class);
        textView.setText(myUser.getUsername().toString());
        return view;
    }


    private void initView() {
        textView= (TextView) view.findViewById(R.id.four_page_frag_tv_name);
        layout1 = (LinearLayout) view.findViewById(R.id.four_page_modify);
        layout1.setOnClickListener(this);
        layout2= (LinearLayout) view.findViewById(R.id.four_page_frag_collection);
        layout2.setOnClickListener(this);
        layout2= (LinearLayout) view.findViewById(R.id.four_page_myFood);
        layout2.setOnClickListener(this);
        layout2= (LinearLayout) view.findViewById(R.id.four_page_myPost);
        layout2.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //放置关于软件
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.four_page_modify:
                startActivity(new Intent(getContext(), ModifyPswActivity.class));
                break;
            case R.id.four_page_frag_collection:
                startActivity(new Intent(getContext(), MyCollectionActivity.class));
                break;
            case R.id.four_page_myFood:
                startActivity(new Intent(getContext(), MyFoodActivity.class));
                break;
            case R.id.four_page_myPost:
                startActivity(new Intent(getContext(), MyPostActivity.class));
                break;
        }
    }
}
