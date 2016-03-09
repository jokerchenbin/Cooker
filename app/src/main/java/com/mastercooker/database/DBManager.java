package com.mastercooker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.mastercooker.R;
import com.mastercooker.model.CookStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBManager {

    public static final String DB_NAME = "mastercooker";
    public static final int BUGGER_SIZE = 1024 * 1024;
    public static final String PACKAGE_NAME = "com.mastercooker";
    //在手机里存放数据库的位置
    public static final String DB_PATH = "/data" +
            Environment.getDataDirectory().getPath() +
            "/" +
            PACKAGE_NAME;

    private SQLiteDatabase sqLiteDatabase;

    private Context mContext;

    public DBManager(Context mContext) {
        this.mContext = mContext;
    }

    public void openDataBase() {
        this.sqLiteDatabase = openDataBase(DB_PATH + "/" + CookStore.DATA_NAME);
        Log.i("SQLiteDatabase", "------------openDataBase ");
    }

    private SQLiteDatabase openDataBase(String DBfile) {
        try {
            //判断数据库文件是否存在,若不存在则执行导入,否则直接打开数据库
            File file = new File(DBfile);
            if (!file.exists()) {
                //欲导入的数据库
                InputStream inputStream = mContext.getResources().openRawResource(R.raw.mastercooker);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bytes = new byte[BUGGER_SIZE];
                int count = 0;
                while ((count = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, count);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                inputStream.close();
            }
            SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(DBfile, null);
            return sqLiteDatabase;
        } catch (FileNotFoundException e) {
            Log.i("Main", "openDataBase ---------File Not Found!" + e.getMessage());
        } catch (IOException e) {
            Log.i("Main", "openDataBase ---------IOException!");
        }
        return null;
    }

    public void closeDataBase() {
        this.sqLiteDatabase.close();
        Log.i("SQLiteDatabase", "---------closeDataBase ");
    }
}
