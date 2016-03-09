package com.mastercooker.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.util.Log;

import com.mastercooker.adapter.CookAdapter;
import com.mastercooker.adapter.FoodStyleAdapter;
import com.mastercooker.adapter.CookLogAdapter;
import com.mastercooker.async.download.DownLoad;
import com.mastercooker.database.DBCookOperation;
import com.mastercooker.model.Cook;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class AsyncDownLoad extends AsyncTask<Void, Void, ArrayList<Cook>> {

    public final static int IS_CLASSIFY = 0;
    public final static int IS_SHOW = 1;
    public final static int IS_LIST = 2;
    public final static int IS_NAME = 3;
    private Context mContext;
    private String name;
    private int page = 0;
    private int duration;
    public AsyncDownLoad(Context mContext, String name, @Duration int duration) {
        this.mContext = mContext;
        this.name = name;
        this.duration = duration;

    }

    public AsyncDownLoad(Context mContext, int page, @Duration int duration) {
        this.mContext = mContext;
        this.page = page;
        this.duration = duration;
    }

    public AsyncDownLoad(int page, @Duration int duration,CookAdapter mCookAdapter) {
        this.page = page;
        this.duration = duration;
    }

    public AsyncDownLoad(String name, @Duration int duration) {
        this.name = name;
        this.duration = duration;
    }

    @Override
    protected ArrayList<Cook> doInBackground(Void... params) {
        switch (duration) {
            case IS_CLASSIFY:
                //return new DownLoad().classifyDownload();
                return null;
            case IS_SHOW:
                return new DownLoad().showDownload(page);
            case IS_LIST:
                return new DownLoad().listDownload(page);
            case IS_NAME:
                return new DownLoad().nameDownload(name);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(ArrayList<Cook> cooks) {
        DBCookOperation dbCookOperation = new DBCookOperation(mContext);
        if (null == cooks || cooks.size() == 0) return;
        for (Cook cook : cooks
                ) {
            StringBuilder
            stringBuffer = new StringBuilder("");
            for(int j= 4 ;j>Integer.toString(page).length();j--){
                stringBuffer.append("0");
            }
            String  s="a"+stringBuffer+page;
            cook.setImages(s);
            Log.i("Main", "onPostExecute ----"+cook.toString());
            dbCookOperation.AddCook(cook);
        }
        super.onPostExecute(cooks);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled(ArrayList<Cook> cooks) {
        super.onCancelled(cooks);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @IntDef({IS_CLASSIFY, IS_SHOW, IS_LIST, IS_NAME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }
}
