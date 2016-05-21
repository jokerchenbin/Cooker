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
import com.mastercooker.model.Cook;
import com.mastercooker.model.MyUser;
import com.mastercooker.model.Util;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.ToastDiy;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.logging.Handler;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MessageActivity extends AppCompatActivity {

    private TextView mTextViewNull, mTextViewDescribe, mTextViewFood, mTextViewKeyWords,
            mTextViewMessage, mTextViewTitle;
    private ImageView mImageView, mImageViewCollect;
    private Cook mCook;
    private Context context;
    private Activity activity;
    private Boolean isCollect=false;
    //private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        context = this;
        activity = this;
        initView();
        initData();
        getData();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.activity_message_iv);
        mTextViewDescribe = (TextView) findViewById(R.id.activity_message_tv_describe);
        mTextViewFood = (TextView) findViewById(R.id.activity_message_tv_food);
        mTextViewKeyWords = (TextView) findViewById(R.id.activity_message_tv_keywords);
        mTextViewMessage = (TextView) findViewById(R.id.activity_message_tv_message);
        mTextViewTitle = (TextView) findViewById(R.id.activity_message_tb_tv_title);
        mImageViewCollect = (ImageView) findViewById(R.id.activity_message_tb_iv);
    }

    private void initData() {
        Intent intent = getIntent();
        mCook = (Cook) intent.getSerializableExtra("Cook");
        mTextViewTitle.setText(mCook.getName());
        ImageLoader.getInstance().displayImage(mCook.getFile().getFileUrl(this), mImageView);
        mTextViewDescribe.setText(mCook.getDescription());
        mTextViewFood.setText(mCook.getFood());
        mTextViewKeyWords.setText(mCook.getKeywords());
        //需要将html文本转换
        mTextViewMessage.setText(Html.fromHtml(mCook.getMessage()));
        mTextViewMessage.setMovementMethod(ScrollingMovementMethod.getInstance());//滚动
    }

    public void onClickCollect(View view) {
        if (!isCollect) {
            collectFood();
        } else {
            cancelCollect();
        }
    }
    private void collectFood(){
        FunctionUtils.showLoadingDialog(this);
        MyUser myUser= BmobUser.getCurrentUser(context,MyUser.class);
        MyUser user=new MyUser();
        user.setObjectId(myUser.getObjectId());
        Cook cook=new Cook();
        cook.setObjectId(mCook.getObjectId());
        BmobRelation relation=new BmobRelation();
        relation.add(cook);
        user.setLikes(relation);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                isCollect=true;
                mImageViewCollect.setImageResource(R.mipmap.is_collect_yes);
                Toast.makeText(context, "收藏成功!", Toast.LENGTH_SHORT).show();
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastDiy.showShort(context, s);
            }
        });
    }
    private void cancelCollect(){
        FunctionUtils.showLoadingDialog(this);
        MyUser myUser= BmobUser.getCurrentUser(context,MyUser.class);
        MyUser user=new MyUser();
        user.setObjectId(myUser.getObjectId());
        Cook cook=new Cook();
        cook.setObjectId(mCook.getObjectId());
        BmobRelation relation=new BmobRelation();
        relation.remove(cook);
        user.setLikes(relation);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                isCollect = false;
                mImageViewCollect.setImageResource(R.mipmap.is_collect_not);
                Toast.makeText(context, "取消收藏!", Toast.LENGTH_SHORT).show();
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastDiy.showShort(context, s);
            }
        });
    }
    /**
     * 查询数据
     */
    private void getData() {
        FunctionUtils.showLoadingDialog(this);
        BmobQuery<Cook> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(context, MyUser.class);
        query.addWhereRelatedTo("likes", new BmobPointer(myUser));
        query.findObjects(context, new FindListener<Cook>() {
            @Override
            public void onSuccess(List<Cook> list) {
                for (Cook info : list) {
                    if (info.getObjectId().equals(mCook.getObjectId())) {
                        isCollect = true;
                        mImageViewCollect.setImageResource(R.mipmap.is_collect_yes);
                        FunctionUtils.dissmisLoadingDialog();
                        return;
                    }
                }
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    public void onClickBack(View view) {
        this.finish();
    }

}
