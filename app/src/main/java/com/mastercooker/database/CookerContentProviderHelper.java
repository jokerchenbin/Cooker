package com.mastercooker.database;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.mastercooker.model.CookLog;
import com.mastercooker.model.Util;

import java.util.ArrayList;

public class CookerContentProviderHelper {

    private Context mContext;

    public CookerContentProviderHelper() {
    }

    public CookerContentProviderHelper(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<CookLog> queryAll() {
        Cursor cursor = null;
        ContentResolver contentResolver = mContext.getContentResolver();

        Uri uri = Uri.parse("content://com.mastercooker/mastercooker");
        cursor = contentResolver.query(uri, Util.getCookLogColumns(), null, null, null);
        ArrayList<CookLog> cookLogs = new ArrayList<>();
        while (cursor.moveToNext()) {
            CookLog cookLog = new CookLog();
            cookLog.setIs_love(cursor.getString(9));
            cookLog.setKeywords(cursor.getString(8));
            cookLog.setIs_hate(cursor.getString(10));
            cookLog.setCount(cursor.getInt(11));
            cookLog.setLook_times(cursor.getInt(7));
            cookLog.setOne(cursor.getString(4));
            cookLog.setThree(cursor.getString(2));
            cookLog.setTwo(cursor.getString(1));
            cookLog.setDescription(cursor.getString(12));
            cookLog.setR_count(cursor.getInt(3));
            cookLog.setMessage(cursor.getString(6));
            cookLog.setImg(cursor.getString(13));
            cookLog.setImages(cursor.getString(14));
            cookLog.setId(cursor.getInt(0));
            cookLog.setFood(cursor.getString(16));
            cookLog.setName(cursor.getString(5));
            cookLog.setF_count(cursor.getInt(15));
            cookLogs.add(cookLog);
        }
        cursor.close();
        return cookLogs;
    }


    public int update(CookLog cookLog) {
        ContentResolver  contentResolver = mContext.getContentResolver();

        Uri uri = Uri.parse("content://com.mastercooker/mastercooker/"+cookLog.getId());
        int i= contentResolver.update(uri,DBCookOperation.getCookLogContentValues(cookLog),null,null);
        return i;
    }
}
