package com.mastercooker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mastercooker.model.CookStore;

public class DBCookHelper extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    private final static String NAME = "mastercooker";

    public DBCookHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = " CREATE TABLE " +
                NAME +
                "(" +
                CookStore.ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CookStore.COUNT +
                " INTEGER," +
                CookStore.R_COUNT +
                " INTEGER," +
                CookStore.F_COUNT +
                " INTEGER," +
                CookStore.NAME +
                " TEXT," +
                CookStore.IMG +
                " TEXT," +
                CookStore.IMAGES +
                " TEXT," +
                CookStore.DESCRIPTION +
                " TEXT," +
                CookStore.FOOD +
                " TEXT,"+
                CookStore.KEYWORDS +
                " TEXT," +
                CookStore.MESSAGE +
                " TEXT);";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库
    }
}
