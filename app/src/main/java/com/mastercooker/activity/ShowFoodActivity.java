package com.mastercooker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

public class ShowFoodActivity extends AppCompatActivity {
    private int type;
    private String typeName;
    private Context context;
    private Activity activity;
    private TextView textView;
    private ListView mListView;
    private CookInfoAdapter mAdapter;
    private List<Cook> cookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food);
        context = this;
        activity = this;
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        typeName=intent.getStringExtra("typeName");
        initView();
        getData();
    }

    private void getData() {
        FunctionUtils.showLoadingDialog(this);
        BmobQuery<Cook> query = new BmobQuery<>();
        query.addWhereEqualTo("type",type);
        query.findObjects(context, new FindListener<Cook>() {
            @Override
            public void onSuccess(List<Cook> list) {
                mAdapter = new CookInfoAdapter(context,list);
                mListView.setAdapter(mAdapter);
                cookList=list;
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.activity_show_food_list);
        textView= (TextView) findViewById(R.id.activity_show_food_title);
        textView.setText(typeName);
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
