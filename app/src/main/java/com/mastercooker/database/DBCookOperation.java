package com.mastercooker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mastercooker.model.Cook;
import com.mastercooker.model.CookLog;
import com.mastercooker.model.CookStore;
import com.mastercooker.model.Util;

import java.util.ArrayList;

public class DBCookOperation {
    /**
     * 通过什么来查询
     */

    private Context mContext;
    private SQLiteDatabase sqLiteDatabase;
    private Cook cook;
    private ArrayList<Cook> cooks;

    public DBCookOperation(Context mContext) {
        this.mContext = mContext;
        sqLiteDatabase = new DBCookHelper(mContext).getReadableDatabase();
    }

    public static void AddAll(SQLiteDatabase sqLiteDatabase, ArrayList<CookLog> cookLogs) {
        for (int i = 0; i < cookLogs.size(); i++) {
            CookLog cookLog = cookLogs.get(i);
            ContentValues values = getCookLogContentValues(cookLog);
            long id = sqLiteDatabase.insert(CookStore.DATA_NAME, null, values);
        }
    }

    public static ContentValues getCookContentValues(Cook cook) {
        ContentValues values = new ContentValues();
        values.put(CookStore.ID, cook.getId());
        values.put(CookStore.COUNT, cook.getCount());
        values.put(CookStore.R_COUNT, cook.getR_count());
        values.put(CookStore.F_COUNT, cook.getF_count());

        values.put(CookStore.NAME, cook.getName());
        values.put(CookStore.KEYWORDS, cook.getKeywords());
        values.put(CookStore.MESSAGE, cook.getMessage());
        values.put(CookStore.DESCRIPTION, cook.getDescription());
        values.put(CookStore.FOOD, cook.getFood());
        values.put(CookStore.IMAGES, cook.getImages());
        values.put(CookStore.IMG, cook.getImg());
        return values;
    }

    public static ContentValues getCookLogContentValues(CookLog cookLog) {
        ContentValues values = new ContentValues();
        values.put(CookStore.ID, cookLog.getId());
        values.put(CookStore.COUNT, cookLog.getCount());
        values.put(CookStore.R_COUNT, cookLog.getR_count());
        values.put(CookStore.F_COUNT, cookLog.getF_count());

        values.put(CookStore.NAME, cookLog.getName());
        values.put(CookStore.KEYWORDS, cookLog.getKeywords());
        values.put(CookStore.MESSAGE, cookLog.getMessage());
        values.put(CookStore.DESCRIPTION, cookLog.getDescription());
        values.put(CookStore.FOOD, cookLog.getFood());
        values.put(CookStore.IMAGES, cookLog.getImages());
        values.put(CookStore.IMG, cookLog.getImg());

        values.put(CookStore.LOOK_TIMES, cookLog.getLook_times());
        values.put(CookStore.IS_HATE, cookLog.getIs_hate());
        values.put(CookStore.IS_LOVE, cookLog.getIs_love());
        values.put(CookStore.ONE, cookLog.getOne());
        values.put(CookStore.TWO, cookLog.getTwo());
        values.put(CookStore.THREE, cookLog.getThree());
        return values;
    }

    public void AddCook(Cook cook) {
        ContentValues values = getCookContentValues(cook);
        long id = sqLiteDatabase.insert(CookStore.DATA_NAME, null, values);
        sqLiteDatabase.close();
    }

    public ArrayList<Cook> queryOne(int id) {
        Cursor cursor = sqLiteDatabase.query(CookStore.DATA_NAME, Util.getCookColumns(), "_id=?", new String[]{Integer.toString(id)}, null, null, null);
        ArrayList<Cook> cooks = fromCursor(cursor);
        sqLiteDatabase.close();
        return cooks;
    }

    public ArrayList<Cook> QueryAll() {
        Cursor cursor = null;
        cursor = sqLiteDatabase.query(CookStore.DATA_NAME, Util.getCookColumns(), null, null, null, null, null);
        ArrayList<Cook> cooks = fromCursor(cursor);
        sqLiteDatabase.close();
        return cooks;
    }

    public void delete(Cook cook) {

        long l = sqLiteDatabase.delete(CookStore.DATA_NAME, "_id=?", new String[]{Integer.toString(cook.getId())});

    }

    public void AddCooks(Context context) {
        DBCookHelper dbCookHelper = new DBCookHelper(context);
        SQLiteDatabase sqLiteDatabase = dbCookHelper.getWritableDatabase();
        for (int i = 0; i < cooks.size(); i++) {
            Cook cook = cooks.get(i);
            ContentValues values = getCookContentValues(cook);
            long id = sqLiteDatabase.insert(CookStore.DATA_NAME, null, values);
        }
    }

    private ArrayList<Cook> fromCursor(Cursor cursor) {
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
        return cooks;
    }


}
