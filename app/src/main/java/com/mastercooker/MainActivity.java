package com.mastercooker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastercooker.database.DBCookOperation;
import com.mastercooker.database.DBManager;
import com.mastercooker.fragment.FirstPageFrag;
import com.mastercooker.fragment.FourthPageFrag;
import com.mastercooker.fragment.SecondPageFragment;
import com.mastercooker.fragment.ThirdPageFrag;
import com.mastercooker.model.CookStore;
import com.mastercooker.model.Util;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "Main";

    //private RecyclerView mRecyclerView;

    private Toolbar mToolbar;
    private ImageView mImageViewFirst, mImageViewSecond, mImageViewThird, mImageViewFourth;
    private TextView mTextView;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        mToolbar = (Toolbar)findViewById(R.id.activity_main_tb);
        mToolbar.addView(view);
        setSupportActionBar(mToolbar);*/


        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheSizePercentage(30)
                .build();
        ImageLoader.getInstance().init(imageLoaderConfiguration);

        mTextView = (TextView) findViewById(R.id.activity_main_tb_tv_title);
        mImageViewFirst = (ImageView) findViewById(R.id.activity_main_ll_iv_first);
        mImageViewSecond = (ImageView) findViewById(R.id.activity_main_ll_iv_second);
        mImageViewThird = (ImageView) findViewById(R.id.activity_main_ll_iv_third);
        mImageViewFourth = (ImageView) findViewById(R.id.activity_main_ll_iv_fourth);

        /*mRecyclerView = (RecyclerView)findViewById(R.id.activity_main_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        ItemMenu itemMenuMain = new ItemMenu("首页",R.mipmap.ic_first_page_gray,R.mipmap.ic_first_page_pink);
        ItemMenu itemMenuSearch = new ItemMenu("搜索",R.mipmap.ic_search_gray,R.mipmap.ic_search_purple);
        ItemMenu itemMenuCollection = new ItemMenu("收藏",R.mipmap.ic_collection_gray,R.mipmap.ic_collection_purple);
        ItemMenu itemMenuAbout = new ItemMenu("关于我",R.mipmap.ic_about_gray,R.mipmap.ic_about_purple);

        MenuAdapter menuAdapter = new MenuAdapter(this);
        menuAdapter.add(itemMenuMain);
        menuAdapter.add(itemMenuSearch);
        menuAdapter.add(itemMenuCollection);
        menuAdapter.add(itemMenuAbout);

        menuAdapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void itemClick(View view) {

            }
        });

        mRecyclerView.setAdapter(menuAdapter);*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment firstPageFragment = FirstPageFrag.newInstance();
        Fragment secondPageFragment = SecondPageFragment.newInstance();
        Fragment thirdPageFrag = ThirdPageFrag.newInstance();
        Fragment fourthPageFrag = FourthPageFrag.newInstance();
        fragmentTransaction.add(R.id.activity_main_fl_parent, firstPageFragment, "first");
        fragmentTransaction.add(R.id.activity_main_fl_parent, secondPageFragment, "second");
        fragmentTransaction.add(R.id.activity_main_fl_parent, thirdPageFrag, "third");
        fragmentTransaction.add(R.id.activity_main_fl_parent, fourthPageFrag, "four");

        fragmentTransaction.show(firstPageFragment);
        fragmentTransaction.hide(secondPageFragment);
        fragmentTransaction.hide(thirdPageFrag);
        fragmentTransaction.hide(fourthPageFrag);

       /* findViewById(R.id.activity_main_wtib_bottom_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick --------1");
                fragmentTransaction.show(firstPageFragment);
                fragmentTransaction.hide(secondPageFragment);
                fragmentTransaction.hide(thirdPageFrag);
                fragmentTransaction.hide(fourthPageFrag);
            }
        });
        findViewById(R.id.activity_main_wtib_bottom_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick --------2");
                fragmentTransaction.hide(firstPageFragment);
                fragmentTransaction.show(secondPageFragment);
                fragmentTransaction.hide(thirdPageFrag);
                fragmentTransaction.hide(fourthPageFrag);
            }
        });findViewById(R.id.activity_main_wtib_bottom_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick --------3");
                fragmentTransaction.hide(firstPageFragment);
                fragmentTransaction.hide(secondPageFragment);
                fragmentTransaction.show(thirdPageFrag);
                fragmentTransaction.hide(fourthPageFrag);
            }
        });findViewById(R.id.activity_main_wtib_bottom_fourth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick --------4");
                fragmentTransaction.hide(firstPageFragment);
                fragmentTransaction.hide(secondPageFragment);
                fragmentTransaction.hide(thirdPageFrag);
                fragmentTransaction.show(fourthPageFrag);
            }
        });*/

        fragmentTransaction.commit();
/*//可以
        int id = R.mipmap.ic_about_gray;
        //保存sharePreference
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);*/


       /*    //download
     for (int i = 1;i<=3000;i++){
            AsyncDownLoad asyncDownLoad = new AsyncDownLoad(this,i,AsyncDownLoad.IS_SHOW);
            asyncDownLoad.execute();
        }*/

        //导入外部数据库
        dbManager = new DBManager(this);
        dbManager.openDataBase();
        dbManager.closeDataBase();




     /*   String  name = getResources().getResourceName(R.drawable.first_page_pink);
        String   entryName= getResources().getResourceEntryName(R.drawable.first_page_pink);
        String   packageName= getResources().getResourcePackageName(R.drawable.first_page_pink);
        String   typeName= getResources().getResourceTypeName(R.drawable.first_page_pink);

        Log.i(TAG, name+"-----------"+entryName+"---------\n"+packageName+
        "--------"+typeName);

         //从drawable中通过文件名读取图片

         String bitmapName = "com.mastercooker:drawable/"+"fd87f22a1beeb8804790ba2f5dc4b3e1";
              Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                      getResources().getIdentifier(bitmapName,
                              "drawable",
                              "com.mastercooker"));

        ImageView imageView = (ImageView)findViewById(R.id.iv);
        imageView.setImageBitmap(bitmap);*/

        //new Async(this).execute();
    }

    public void onViewClick(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment firstPageFragment = fragmentManager.findFragmentByTag("first");
        Fragment secondPageFragment = fragmentManager.findFragmentByTag("second");
        Fragment thirdPageFrag = fragmentManager.findFragmentByTag("third");
        Fragment fourthPageFrag = fragmentManager.findFragmentByTag("four");
        switch (v.getId()) {
            case R.id.activity_main_ll_iv_first:
                Log.i(TAG, "onViewClick ---------" + v.getId());
                fragmentTransaction.show(firstPageFragment);
                fragmentTransaction.hide(secondPageFragment);
                fragmentTransaction.hide(thirdPageFrag);
                fragmentTransaction.hide(fourthPageFrag);
                mImageViewFirst.setImageResource(R.mipmap.ic_first_page_pink);
                mImageViewSecond.setImageResource(R.mipmap.ic_search_gray);
                mImageViewThird.setImageResource(R.mipmap.ic_collection_gray);
                mImageViewFourth.setImageResource(R.mipmap.ic_about_gray);
                mTextView.setText("首页");
                break;
            case R.id.activity_main_ll_iv_second:
                Log.i(TAG, "onViewClick ---------" + v.getId());
                fragmentTransaction.hide(firstPageFragment);
                fragmentTransaction.show(secondPageFragment);
                fragmentTransaction.hide(thirdPageFrag);
                fragmentTransaction.hide(fourthPageFrag);
                mImageViewFirst.setImageResource(R.mipmap.ic_first_page_gray);
                mImageViewSecond.setImageResource(R.mipmap.ic_search_purple);
                mImageViewThird.setImageResource(R.mipmap.ic_collection_gray);
                mImageViewFourth.setImageResource(R.mipmap.ic_about_gray);
                mTextView.setText("搜索");
                break;
            case R.id.activity_main_ll_iv_third:
                Log.i(TAG, "onViewClick ---------" + v.getId());
                fragmentTransaction.hide(firstPageFragment);
                fragmentTransaction.hide(secondPageFragment);
                fragmentTransaction.show(thirdPageFrag);
                fragmentTransaction.hide(fourthPageFrag);
                mImageViewFirst.setImageResource(R.mipmap.ic_first_page_gray);
                mImageViewSecond.setImageResource(R.mipmap.ic_search_gray);
                mImageViewThird.setImageResource(R.mipmap.ic_collection_purple);
                mImageViewFourth.setImageResource(R.mipmap.ic_about_gray);
                mTextView.setText("收藏");
                break;
            case R.id.activity_main_ll_iv_fourth:
                Log.i(TAG, "onViewClick ---------" + v.getId());
                fragmentTransaction.hide(firstPageFragment);
                fragmentTransaction.hide(secondPageFragment);
                fragmentTransaction.hide(thirdPageFrag);
                fragmentTransaction.show(fourthPageFrag);
                mImageViewFirst.setImageResource(R.mipmap.ic_first_page_gray);
                mImageViewSecond.setImageResource(R.mipmap.ic_search_gray);
                mImageViewThird.setImageResource(R.mipmap.ic_collection_gray);
                mImageViewFourth.setImageResource(R.mipmap.ic_about_purple);
                mTextView.setText("关于软件");
                break;

        }
        fragmentTransaction.commit();
    }

    /**
     * downloadImage
     */
    public class Async extends AsyncTask<Void, Void, Bitmap> {

        DBManager dbManager;
        private DisplayImageOptions options;
        private Context mContext;
        private String path = Environment.getExternalStorageDirectory().getPath();

        public Async(Context mContext) {
            this.mContext = mContext;
            dbManager = new DBManager(mContext);
            dbManager.openDataBase();
            options = new DisplayImageOptions
                    .Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .build();
        }

        public Async() {
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            File file = new File(path + "/image/My");
            if (!file.exists())
                file.mkdirs();
            SQLiteDatabase sqLiteDatabase =
                    SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH +
                            "/" + DBManager.DB_NAME, null);
            Cursor cursor = sqLiteDatabase.query(CookStore.DATA_NAME, Util.getCookColumns(), null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String img = cursor.getString(3);
                String name = cursor.getString(cursor.getColumnIndex(CookStore.IMAGES));
                String url = "http://tnfs.tngou.net/image" + img ;
                Log.i(TAG, id + "--------" + img + "----" + name + "----" + url);
                Bitmap bitmap = ImageLoader.getInstance().loadImageSync(url, options);
                if (null == bitmap)
                    continue;
               /* ContentValues values = new ContentValues();
                values.put(CookStore.IMAGES,name);
                int l = sqLiteDatabase.update(CookStore.DATA_NAME,values,"_id=?",new String[]{Integer.toString(id)});
                */
                try {
                    File f = new File(file, "/" + name + ".jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.i(TAG, "doInBackground ------FileNotFoundException");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i(TAG, "doInBackground --------IOException");
                }
            }
            cursor.close();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Bitmap bitmap) {
            super.onCancelled(bitmap);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
