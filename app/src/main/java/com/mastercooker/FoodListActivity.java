package com.mastercooker;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mastercooker.adapter.CookAdapter;
import com.mastercooker.database.DBManager;
import com.mastercooker.database.DBManagerHelper;
import com.mastercooker.model.Cook;
import com.mastercooker.model.CookStore;

import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private CookAdapter mCookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        mRecyclerView = (RecyclerView)findViewById(R.id.activity_food_list_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mImageView = (ImageView) findViewById(R.id.activity_food_list_tb_iv_back);
        Intent intentStart = getIntent();
        int startId = intentStart.getIntExtra("startId", 1);

        //从数据库读取数据
        DBManagerHelper dbManagerHelper = new DBManagerHelper(this);
        ArrayList<Cook> cooks = dbManagerHelper.query(startId);

        mCookAdapter = new CookAdapter(this);
        mCookAdapter.addAll(cooks);

        mRecyclerView.setAdapter(mCookAdapter);

        mCookAdapter.setOnItemClickListener(new CookAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Cook cook) {
                Intent intent = new Intent(FoodListActivity.this,MessageActivity.class);
                intent.putExtra("Cook",cook);
                startActivity(intent);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
