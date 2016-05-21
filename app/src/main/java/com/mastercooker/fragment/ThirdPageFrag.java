package com.mastercooker.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.mastercooker.R;
import com.mastercooker.activity.ClassifyPostActivity;
import com.mastercooker.activity.DetailPostActivity;
import com.mastercooker.activity.MessageActivity;
import com.mastercooker.activity.ShowFoodActivity;
import com.mastercooker.adapter.CookInfoAdapter;
import com.mastercooker.adapter.PostAdapter;
import com.mastercooker.model.Cook;
import com.mastercooker.model.Post;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.ToastDiy;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class ThirdPageFrag extends Fragment {
    private View view;
    private ListView mList;
    private List<Post> PostList;
    private TextView textView1,textView2,textView3,textView4;
    private List<TextView> listTextView;

    public ThirdPageFrag() {
        super();
    }

    public static ThirdPageFrag newInstance() {
        return new ThirdPageFrag();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.third_page_frag, container, false);
            initView(view);
        }
        getData();
        return view;
    }

    private void getData() {
        BmobQuery<Post> query = new BmobQuery<>();
        //query.order("createdAt");
        query.findObjects(getContext(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                PostList = list;
                mList.setAdapter(new PostAdapter(getContext(), list));
            }

            @Override
            public void onError(int i, String s) {
                ToastDiy.showShort(getContext(), s);
            }
        });
    }

    private void initView(View view) {
        mList= (ListView) view.findViewById(R.id.third_page_frag_list);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailPostActivity.class);
                intent.putExtra("Post", PostList.get(position));
                startActivity(intent);
            }
        });
        textView1= (TextView) view.findViewById(R.id.third_page_frag_type1);
        textView2= (TextView) view.findViewById(R.id.third_page_frag_type2);
        textView3= (TextView) view.findViewById(R.id.third_page_frag_type3);
        textView4= (TextView) view.findViewById(R.id.third_page_frag_type4);
        listTextView = new ArrayList<>();
        listTextView.add(textView1);
        listTextView.add(textView2);
        listTextView.add(textView3);
        listTextView.add(textView4);
        showPost();
    }

    private void showPost() {
        for(int i=0;i<listTextView.size();i++){
            final int num = i;
            listTextView.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(getContext(), ClassifyPostActivity.class);
                    intent.putExtra("typeName",listTextView.get(num).getText().toString().trim());
                    startActivity(intent);
                }
            });
        }
    }
}
