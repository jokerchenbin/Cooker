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
import com.mastercooker.adapter.PostAdapter;
import com.mastercooker.model.Cook;
import com.mastercooker.model.Post;
import com.mastercooker.tools.FunctionUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ClassifyPostActivity extends AppCompatActivity {
    private Context context;
    private Activity activity;
    private TextView textView;
    private ListView mListView;
    private PostAdapter mAdapter;
    private List<Post> postList;
    private String typeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_post);
        context = this;
        activity = this;
        Intent intent=getIntent();
        typeName=intent.getStringExtra("typeName");
        initView();
        getData();
    }

    private void getData() {
        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("postType",typeName);
        query.findObjects(context, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                mAdapter = new PostAdapter(context,list);
                mListView.setAdapter(mAdapter);
                postList=list;
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.activity_classify_post_list);
        textView= (TextView) findViewById(R.id.activity_classify_post_type);
        textView.setText(typeName);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, DetailPostActivity.class);
                intent.putExtra("Post",postList.get(position));
                startActivity(intent);
            }
        });
    }
    public void onClickBack(View view) {
        this.finish();
    }
}
