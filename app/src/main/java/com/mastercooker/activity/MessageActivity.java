package com.mastercooker.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mastercooker.R;
import com.mastercooker.database.DBCookOperation;
import com.mastercooker.model.Cook;
import com.mastercooker.model.Util;

public class MessageActivity extends AppCompatActivity {

    private TextView mTextViewNull, mTextViewDescribe, mTextViewFood, mTextViewKeyWords,
            mTextViewMessage, mTextViewTitle;
    private ImageView mImageView, mImageViewCollect;


    private boolean is_collect = false;
    private boolean is_collected = false;


    private Cook mCookLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        mImageView = (ImageView) findViewById(R.id.activity_message_iv);
        mTextViewDescribe = (TextView) findViewById(R.id.activity_message_tv_describe);
        mTextViewFood = (TextView) findViewById(R.id.activity_message_tv_food);
        mTextViewKeyWords = (TextView) findViewById(R.id.activity_message_tv_keywords);
        mTextViewMessage = (TextView) findViewById(R.id.activity_message_tv_message);
        mTextViewTitle = (TextView) findViewById(R.id.activity_message_tb_tv_title);
        mImageViewCollect = (ImageView) findViewById(R.id.activity_message_tb_iv);

        Intent intent = getIntent();
        mCookLog = (Cook) intent.getSerializableExtra("Cook");
        mTextViewTitle.setText(mCookLog.getName());
        String bitmapName = "com.mastercooker:drawable/" + Util.getZero(mCookLog.getId());
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                getResources().getIdentifier(bitmapName,
                        "drawable",
                        "com.mastercooker"));
        mImageView.setImageBitmap(bitmap);

        //判断是否已经加入收藏
        DBCookOperation dbCookOperation = new DBCookOperation(this);
        if (dbCookOperation.queryOne(mCookLog.getId()).size() > 0) {
            is_collected = true;
            is_collect = true;
            mImageViewCollect.setImageResource(R.mipmap.ic_collection);
        }

        mTextViewDescribe.setText(mCookLog.getDescription());
        mTextViewFood.setText(mCookLog.getFood());
        mTextViewKeyWords.setText(mCookLog.getKeywords());
        //需要将html文本转换
        mTextViewMessage.setText(Html.fromHtml(mCookLog.getMessage()));
        mTextViewMessage.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动

    }

    public void onClickCollect(View view) {
        is_collect = !is_collect;
        if (is_collect) {
            mImageViewCollect.setImageResource(R.mipmap.ic_collection);
            Toast.makeText(this, "收藏成功!", Toast.LENGTH_SHORT).show();
        } else {
            mImageViewCollect.setImageResource(R.mipmap.ic_collection_gray);
            Toast.makeText(this, "取消收藏!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickBack(View view) {
        this.finish();
    }

    @Override
    protected void onStop() {
        DBCookOperation dbCookOperation;
        if (!is_collected && is_collect) {
            dbCookOperation = new DBCookOperation(this);
            dbCookOperation.AddCook(mCookLog);
        } else if (is_collected && !is_collect) {
            dbCookOperation = new DBCookOperation(this);
            dbCookOperation.delete(mCookLog);
        }
        Intent intent = new Intent("com.mastercooker_ACTION");
        sendBroadcast(intent);
        super.onStop();
    }
}
