package com.mastercooker.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mastercooker.model.Cook;
import com.mastercooker.model.CookStore;

import java.util.ArrayList;

public class DBManagerHelper {

    private Context mContext;
    private DBManager dbManager;

    public DBManagerHelper(Context mContext) {
        this.mContext = mContext;
        dbManager = new DBManager(mContext);
    }

    public ArrayList<Cook> query(int startId) {
        dbManager.openDataBase();
        Cursor cursor = null;
        //从startId开始1-100条数据
        SQLiteDatabase sqLiteDatabase =
                SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + CookStore.DATA_NAME, null);

        /*cursor = sqLiteDatabase.query(CookStore.DATA_NAME,
                Util.getCookColumns(), "_id>? and _id<?",
                new String[]{Integer.toString(startId - 1), Integer.toString(startId + 100)},
                null, null, null);*/

        cursor = sqLiteDatabase.rawQuery("SELECT _id, name, food, img, images, description, keywords, message, count, f_count, r_count FROM mastercooker WHERE _id>? and _id<?",
                new String[]{Integer.toString(startId - 1), Integer.toString(startId + 100)});

        ArrayList<Cook> cooks = new ArrayList<>();
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
            cooks.add(cookLog);
        }
        cursor.close();
        sqLiteDatabase.close();
        dbManager.closeDataBase();
        return cooks;
    }


}
