package com.mastercooker.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mastercooker.R;
import com.mastercooker.adapter.CookInfoAdapter;
import com.mastercooker.adapter.PostAdapter;
import com.mastercooker.model.Cook;
import com.mastercooker.model.MyUser;
import com.mastercooker.model.Post;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class MyPostActivity extends AppCompatActivity {
    private Context context;
    private ListView mListView;
    private PostAdapter mAdapter;
    private List<Post> postList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);
        context = this;
        initView();
        getData();
    }

    private void getData() {
        BmobQuery<Post> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(context, MyUser.class);
        query.addWhereEqualTo("postUser", myUser.getUsername().toString());
        query.findObjects(context, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                mAdapter = new PostAdapter(context, list);
                mListView.setAdapter(mAdapter);
                postList = list;
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.activity_my_post_list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, DetailPostActivity.class);
                intent.putExtra("Post", postList.get(position));
                startActivity(intent);
            }
        });
    }
    public void onClickBack(View view) {
        this.finish();
    }
}
