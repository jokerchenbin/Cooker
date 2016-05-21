package com.mastercooker.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mastercooker.activity.MessageActivity;
import com.mastercooker.R;
import com.mastercooker.activity.SearchResultActivity;
import com.mastercooker.adapter.CookAdapter;
import com.mastercooker.adapter.CookInfoAdapter;
import com.mastercooker.model.Cook;
import com.mastercooker.model.CookStore;
import com.mastercooker.model.MyUser;
import com.mastercooker.model.history;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.ToastDiy;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class SecondPageFragment extends Fragment {
    private EditText editText;
    private TextView textView;
    private View view;
    private TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6,tv_7,tv_8,tv_9;
    private List<TextView> textViewList;
    private String searchText;
    private boolean tag=false;

    public SecondPageFragment() {
        super();
    }

    public static SecondPageFragment newInstance() {
        return new SecondPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.second_page_frag, container, false);
        initView();
        showHistory();
        return view;
    }

    private void showHistory() {
        BmobQuery<history> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(getContext(), MyUser.class);
        query.addWhereEqualTo("userID",myUser.getObjectId().toString().trim());
        query.findObjects(getContext(), new FindListener<history>() {
            @Override
            public void onSuccess(List<history> list) {
               for(int i=0;i<list.size();i++){
                   textViewList.get(i).setText(list.get(i).getHistory_name().toString());
               }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private void initView() {
        tv_1= (TextView) view.findViewById(R.id.history1);
        tv_2= (TextView) view.findViewById(R.id.history2);
        tv_3= (TextView) view.findViewById(R.id.history3);
        tv_4= (TextView) view.findViewById(R.id.history4);
        tv_5= (TextView) view.findViewById(R.id.history5);
        tv_6= (TextView) view.findViewById(R.id.history6);
        tv_7= (TextView) view.findViewById(R.id.history7);
        tv_8= (TextView) view.findViewById(R.id.history8);
        tv_9= (TextView) view.findViewById(R.id.history9);
        textViewList=new ArrayList<>();
        textViewList.add(tv_1);
        textViewList.add(tv_2);
        textViewList.add(tv_3);
        textViewList.add(tv_4);
        textViewList.add(tv_5);
        textViewList.add(tv_6);
        textViewList.add(tv_7);
        textViewList.add(tv_8);
        textViewList.add(tv_9);
        for(int i=0;i<textViewList.size();i++){
            final int num=i;
            if (textViewList.get(i) != null){
                textViewList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editText.setText(textViewList.get(num).getText().toString().trim());
                    }
                });
            }else {
                ToastDiy.showShort(getContext(),"i = "+i+" = null");
            }

        }
        editText= (EditText) view.findViewById(R.id.second_page_frag_search);
        textView= (TextView) view.findViewById(R.id.second_page_frag_bt);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText=editText.getText().toString().trim();
                postHistory();
                Intent intent=new Intent();
                intent.setClass(getContext(), SearchResultActivity.class);
                intent.putExtra("word",searchText);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void postHistory() {
        BmobQuery<history> query = new BmobQuery<>();
        final MyUser myUser = BmobUser.getCurrentUser(getContext(), MyUser.class);
        query.addWhereEqualTo("userID",myUser.getObjectId().toString().trim());
        query.findObjects(getContext(), new FindListener<history>() {
            @Override
            public void onSuccess(List<history> list) {
                for(int i=0;i<list.size();i++){
                    if(searchText.equals(list.get(i).getHistory_name().toString()))
                        tag=true;
                }
                if(!tag){
                    history h=new history();
                    h.setHistory_name(searchText);
                    h.setUserID(myUser.getObjectId().toString().trim());
                    h.save(getContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {

                        }
                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
