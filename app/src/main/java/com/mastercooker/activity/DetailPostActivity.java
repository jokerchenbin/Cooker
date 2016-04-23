package com.mastercooker.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mastercooker.R;
import com.mastercooker.adapter.CommentAdapter;
import com.mastercooker.adapter.CookInfoAdapter;
import com.mastercooker.model.Comment;
import com.mastercooker.model.Cook;
import com.mastercooker.model.MyUser;
import com.mastercooker.model.Post;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.ToastDiy;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class DetailPostActivity extends AppCompatActivity implements View.OnClickListener {

    private Post mPost;
    private Context context;
    private Activity activity;
    private TextView tv_type,tv_user,tv_content,tv_comment;
    private EditText ed_comment;
    private ImageView iv_photo;
    private ListView mListView;
    private CommentAdapter mAdapter;
    private List<Comment> commentList;
    private String postID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        context = this;
        activity = this;
        initView();
        initData();
        getData();
    }


    private void getData() {
        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("postID", postID);
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                ViewGroup.LayoutParams params = mListView.getLayoutParams();
                params.height = 250 * list.size();
                mListView.setLayoutParams(params);
                mAdapter = new CommentAdapter(context, list);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initData() {
        Intent intent=getIntent();
        mPost = (Post) intent.getSerializableExtra("Post");
        postID=mPost.getObjectId().toString();
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
        mListView= (ListView) findViewById(R.id.activity_detail_post_list);
        tv_comment= (TextView) findViewById(R.id.activity_detail_post_bt);
        ed_comment= (EditText) findViewById(R.id.activity_detail_ed);
        tv_comment.setOnClickListener(this);
    }


    public void onClickBack(View view) {
        this.finish();
    }


    @Override
    public void onClick(View v) {
        Comment comment=new Comment();
        comment.setPostID(postID);
        comment.setCommentUser(BmobUser.getCurrentUser(context, MyUser.class).getUsername().toString());
        comment.setContent(ed_comment.getText().toString());
        comment.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context,"评论成功");
                BmobQuery<Post> query=new BmobQuery<Post>();
                query.addWhereEqualTo("objectId",postID);
                query.findObjects(context, new FindListener<Post>() {
                    @Override
                    public void onSuccess(List<Post> list) {
                        int number=list.get(0).getCommentNumber();
                        Post post=new Post();
                        post.setCommentNumber(number+1);
                        post.update(context, postID, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                getData();
                                ed_comment.setText("");
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
}
