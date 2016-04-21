package com.mastercooker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastercooker.R;
import com.mastercooker.model.Cook;
import com.mastercooker.model.MyUser;
import com.mastercooker.model.Post;
import com.mastercooker.tools.FunctionUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

public class DetailPostActivity extends AppCompatActivity {

    private Post mPost;
    private Context context;
    private Activity activity;
    private TextView tv_type,tv_user,tv_content;
    private ImageView iv_photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        context = this;
        activity = this;
        initView();
        initData();
    }

/*
    private void getData() {
        BmobQuery<Post> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(context, MyUser.class);
        query.findObjects(context, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                for (Post info : list) {
                    if (info.getObjectId().equals(mPost.getObjectId())) {
                        return;
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
*/

    private void initData() {
        Intent intent=getIntent();
        mPost = (Post) intent.getSerializableExtra("Post");
        tv_type.setText("来自："+mPost.getPostType());
        ImageLoader.getInstance().displayImage(mPost.getImage().getFileUrl(this), iv_photo);
        tv_user.setText(mPost.getPostUser());
        tv_content.setText(mPost.getContent());
    }

    private void initView() {
        tv_type= (TextView) findViewById(R.id.activity_detail_post_tv_type);
        tv_content= (TextView) findViewById(R.id.activity_detail_post_tv_content);
        tv_user= (TextView) findViewById(R.id.activity_detail_post_tv_user);
        iv_photo= (ImageView) findViewById(R.id.activity_detail_post_iv_photo);
    }


    public void onClickBack(View view) {
        this.finish();
    }

    
}
