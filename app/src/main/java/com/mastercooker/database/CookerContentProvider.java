package com.mastercooker.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.mastercooker.model.CookStore;
import com.mastercooker.model.Util;

public class CookerContentProvider extends ContentProvider {
    public final static String AUTHORITY = "com.mastercooker.mastercooker";
    public final static String NOTE = "cook";
    public final static String NOTE_ITEM = "cook/#";

    private final static int CODE_NOTE = 1;
    private final static int CODE_NOTE_ITEM = 2;

    static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        //添加可以接受的URI
        matcher.addURI(AUTHORITY, NOTE, CODE_NOTE);
        matcher.addURI(AUTHORITY, NOTE_ITEM, CODE_NOTE_ITEM);
    }

    private DBManager dbManager;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    public boolean onCreate() {
        dbManager = new DBManager(getContext());
        dbManager.openDataBase();
        sqLiteDatabase =
                SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH +
                        "/" + DBManager.DB_NAME, null);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = matcher.match(uri);
        if (match == CODE_NOTE) {
            return sqLiteDatabase.query(CookStore.DATA_NAME, Util.getCookLogColumns(), selection, selectionArgs, null, null, sortOrder);
        } else if (match == CODE_NOTE_ITEM) {
            long id = ContentUris.parseId(uri);
            return sqLiteDatabase.query(CookStore.DATA_NAME, Util.getCookLogColumns(), null, null, null, null, sortOrder);
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        int match = matcher.match(uri);
        switch (match) {
            case CODE_NOTE:
                return "vnd.android.cursor.dir/vnd.com.mastercooker.mastercooker";
            case CODE_NOTE_ITEM:
                return "vnd.android.cursor.item/vnd.com.mastercooker.mastercooke";
            default:
                break;
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = matcher.match(uri);
        if (match == CODE_NOTE) {
            long id = sqLiteDatabase.insert(CookStore.DATA_NAME, null, values);
            return ContentUris.withAppendedId(uri, id);
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = matcher.match(uri);
        long id = ContentUris.parseId(uri);
        if (match == CODE_NOTE_ITEM) {
            return sqLiteDatabase.delete(CookStore.DATA_NAME, "_id=?", new String[]{Long.toString(id)});
        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int match = matcher.match(uri);
        long id = ContentUris.parseId(uri);
        if (match == CODE_NOTE_ITEM) {
            return sqLiteDatabase.update(CookStore.DATA_NAME, values, " _id=? ", new String[]{Long.toString(id)});
        }
        return 0;
    }


}
