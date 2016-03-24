package com.mastercooker.fragment;

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

import com.mastercooker.activity.MessageActivity;
import com.mastercooker.R;
import com.mastercooker.adapter.CookAdapter;
import com.mastercooker.database.DBManager;
import com.mastercooker.model.Cook;
import com.mastercooker.model.CookStore;


public class SecondPageFragment extends Fragment {

    private CookAdapter cookAdapter;

    public SecondPageFragment() {
        super();
    }

    public static SecondPageFragment newInstance() {
        return new SecondPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_page_frag, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //自定义
        EditText editText = (EditText) view.findViewById(R.id.second_page_frag_et_search);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.second_page_frag_rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        cookAdapter = new CookAdapter(getContext());

        recyclerView.setAdapter(cookAdapter);

        cookAdapter.setOnItemClickListener(new CookAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Cook cook) {
                //跳转界面
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("Cook", cook);
                startActivity(intent);
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cookAdapter.clear();
                if (s != null || s.length() != 0)
                    queryAllForSearch(s.toString());
            }
        });
    }

    public void queryAllForSearch(String container) {
        DBManager dbManager = new DBManager(getContext());
        dbManager.openDataBase();
        Cursor cursor = null;
        //从startId开始1-100条数据
        SQLiteDatabase sqLiteDatabase =
                SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + CookStore.DATA_NAME, null);

        /*cursor = sqLiteDatabase.query(CookStore.DATA_NAME,
                Util.getCookColumns(), "_id>? and _id<?",
                new String[]{Integer.toString(startId - 1), Integer.toString(startId + 100)},
                null, null, null);*/

        cursor = sqLiteDatabase.rawQuery("SELECT _id, name, food, img, images, description, keywords, message, count, f_count, r_count FROM mastercooker",
                null);

        while (cursor.moveToNext()) {
            Cook cookLog = new Cook();
            cookLog.setKeywords(cursor.getString(cursor.getColumnIndex(CookStore.KEYWORDS)));
            cookLog.setCount(cursor.getInt(cursor.getColumnIndex(CookStore.COUNT)));
            cookLog.setDescription(cursor.getString(cursor.getColumnIndex(CookStore.DESCRIPTION)));
            cookLog.setR_count(cursor.getInt(cursor.getColumnIndex(CookStore.R_COUNT)));
            cookLog.setMessage(cursor.getString(cursor.getColumnIndex(CookStore.MESSAGE)));
            cookLog.setImg(cursor.getString(cursor.getColumnIndex(CookStore.IMG)));
            cookLog.setImages(cursor.getString(cursor.getColumnIndex(CookStore.IMAGES)));
            cookLog.setId(cursor.getInt(cursor.getColumnIndex(CookStore.ID)));
            cookLog.setFood(cursor.getString(cursor.getColumnIndex(CookStore.FOOD)));
            cookLog.setName(cursor.getString(cursor.getColumnIndex(CookStore.NAME)));
            cookLog.setF_count(cursor.getInt(cursor.getColumnIndex(CookStore.F_COUNT)));
            if (cookLog.getKeywords().contains(container)
                    && cookLog.getDescription().contains(container)
                    && cookLog.getMessage().contains(container))
                cookAdapter.add(cookLog);
        }
        cursor.close();
        sqLiteDatabase.close();
        dbManager.closeDataBase();
    }
}
