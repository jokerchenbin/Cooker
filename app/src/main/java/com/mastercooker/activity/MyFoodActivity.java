package com.mastercooker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mastercooker.R;
import com.mastercooker.adapter.CookInfoAdapter;
import com.mastercooker.model.Cook;
import com.mastercooker.model.MyUser;
import com.mastercooker.tools.FunctionUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

public class MyFoodActivity extends AppCompatActivity {

    private Context context;
    private ListView mListView;
    private CookInfoAdapter mAdapter;
    private List<Cook> cookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_food);
        context = this;
        initView();
        getData();
    }

    private void getData() {
        BmobQuery<Cook> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(context, MyUser.class);
        query.addWhereEqualTo("postName",myUser.getUsername().toString());
        query.findObjects(context, new FindListener<Cook>() {
            @Override
            public void onSuccess(List<Cook> list) {
                mAdapter = new CookInfoAdapter(context,list);
                mListView.setAdapter(mAdapter);
                cookList=list;
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.activity_my_food_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("Cook",cookList.get(position));
                startActivity(intent);
            }
        });
    }
    public void onClickBack(View view) {
        this.finish();
    }
}
