package com.mastercooker.activity;

import android.app.Activity;
import android.content.Context;
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
import com.nostra13.universalimageloader.core.ImageLoader;

public class MessageActivity extends AppCompatActivity {

    private TextView mTextViewNull, mTextViewDescribe, mTextViewFood, mTextViewKeyWords,
            mTextViewMessage, mTextViewTitle;
    private ImageView mImageView, mImageViewCollect;
    private boolean is_collect = false;
    private boolean is_collected = false;
    private Cook mCookLog;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        context = this;
        activity = this;
        initView();
        initData();

    }

    /**
     * Created by 陈彬 on 2016/3/29  17:18
     * 方法描述: 初始化视图
     */
    private void initView() {
        mImageView = (ImageView) findViewById(R.id.activity_message_iv);
        mTextViewDescribe = (TextView) findViewById(R.id.activity_message_tv_describe);
        mTextViewFood = (TextView) findViewById(R.id.activity_message_tv_food);
        mTextViewKeyWords = (TextView) findViewById(R.id.activity_message_tv_keywords);
        mTextViewMessage = (TextView) findViewById(R.id.activity_message_tv_message);
        mTextViewTitle = (TextView) findViewById(R.id.activity_message_tb_tv_title);
        mImageViewCollect = (ImageView) findViewById(R.id.activity_message_tb_iv);
    }

    /**
     * Created by 陈彬 on 2016/3/29  17:18
     * 方法描述: 初始化数据
     */
    private void initData() {
        Intent intent = getIntent();
        mCookLog = (Cook) intent.getSerializableExtra("Cook");
        mTextViewTitle.setText(mCookLog.getName());
        ImageLoader.getInstance().displayImage(mCookLog.getFile().getFileUrl(this), mImageView);
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

}
