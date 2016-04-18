package com.mastercooker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mastercooker.R;
import com.mastercooker.activity.ModifyPswActivity;
import com.mastercooker.activity.MyCollectionActivity;

public class FourthPageFrag extends Fragment implements View.OnClickListener {
    private LinearLayout layout1,layout2;
    private View view;

    public static FourthPageFrag newInstance() {
        return new FourthPageFrag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.four_page_frag, container, false);
        initView();
        return view;
    }


    private void initView() {
        layout1 = (LinearLayout) view.findViewById(R.id.four_page_modify);
        layout1.setOnClickListener(this);
        layout2= (LinearLayout) view.findViewById(R.id.four_page_frag_colletion);
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
            case R.id.four_page_frag_colletion:
                startActivity(new Intent(getContext(), MyCollectionActivity.class));
                break;
        }
    }
}
