package com.mastercooker.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mastercooker.MessageActivity;
import com.mastercooker.R;
import com.mastercooker.adapter.CookAdapter;
import com.mastercooker.database.DBCookOperation;
import com.mastercooker.model.Cook;
import com.mastercooker.view.WithTextImageButton;

import java.util.ArrayList;

public class ThirdPageFrag extends Fragment {

    private CookAdapter cookLogAdapter;
    private WithTextImageButton withTextImageButton;
    private RecyclerView recyclerView;
    private Receiver mReceiver;

    public static ThirdPageFrag newInstance() {
        return new ThirdPageFrag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.third_page_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        withTextImageButton = (WithTextImageButton) view.findViewById(R.id.activity_main_wtib_bottom_fourth);
        recyclerView = (RecyclerView) view.findViewById(R.id.third_page_frag_rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        cookLogAdapter = new CookAdapter(getContext());
        recyclerView.setAdapter(cookLogAdapter);

        fresh();

        cookLogAdapter.setOnItemClickListener(new CookAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Cook cook) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("Cook", cook);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onResume() {
        fresh();
        super.onResume();
    }

    @Override
    public void onStart() {
        fresh();
         //在代码中注册Receiver,一定要结束在onDestroy(如果在onStart中注册,则需要在onStop中结束
        mReceiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.mastercooker_ACTION");
        getActivity().registerReceiver(mReceiver, intentFilter);
        super.onStart();
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mReceiver);
        super.onStop();
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        onView();
    }

    public void onView() {
        if (cookLogAdapter.getItemCount() > 0) {
            withTextImageButton.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            withTextImageButton.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    public void fresh() {
        DBCookOperation dbCookOperation = new DBCookOperation(getContext());
        ArrayList<Cook> cooks = dbCookOperation.QueryAll();
        cookLogAdapter.addAll(cooks);
        onView();
    }

    public class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            fresh();
        }
    }
}
