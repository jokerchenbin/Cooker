package com.mastercooker.activity;

import android.app.Activity;
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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mastercooker.R;
import com.mastercooker.adapter.FragmentAdapter;
import com.mastercooker.fragment.FirstPageFrag;
import com.mastercooker.fragment.FourthPageFrag;
import com.mastercooker.fragment.SecondPageFragment;
import com.mastercooker.fragment.ThirdPageFrag;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "Main";
    private List<Fragment> listfraFragments;
    private FirstPageFrag firstPageFrag;
    private SecondPageFragment secondPageFragment;
    private ThirdPageFrag thirdPageFrag;
    private FourthPageFrag fourthPageFrag;
    private RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5;
    private  ViewPager viewPager;
    private TextView textView;
    private ImageView imageView;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        activity = this;
        initView();


    }


    private void initView() {
        imageView=(ImageView)findViewById(R.id.activity_bar_image);
        textView=(TextView)findViewById(R.id.activity_main_tb_tv_title);
        radioButton1 = (RadioButton) findViewById(R.id.radiobutton1);
        radioButton2 = (RadioButton) findViewById(R.id.radiobutton2);
        radioButton3 = (RadioButton) findViewById(R.id.radiobutton3);
        radioButton4 = (RadioButton) findViewById(R.id.radiobutton4);
       // radioButton5 = (RadioButton) findViewById(R.id.radiobutton_add);
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
        //radioButton5.setOnClickListener(this);
        viewPager = (ViewPager) findViewById(R.id.activity_main_framelayout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        firstPageFrag = new FirstPageFrag();
        secondPageFragment = new SecondPageFragment();
        thirdPageFrag = new ThirdPageFrag();
        fourthPageFrag = new FourthPageFrag();
        listfraFragments = new ArrayList<>();
        listfraFragments.add(firstPageFrag);
        listfraFragments.add(secondPageFragment);
        listfraFragments.add(thirdPageFrag);
        listfraFragments.add(fourthPageFrag);
        FragmentAdapter fadpter = new FragmentAdapter(fragmentManager, listfraFragments);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(fadpter);
        viewPager.setCurrentItem(0);

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radiobutton1://首页
                viewPager.setCurrentItem(0);
                textView.setText(R.string.FirstPage);
                imageView.setImageResource(R.mipmap.home_ico);
                break;
            case R.id.radiobutton2: // 搜索
                viewPager.setCurrentItem(1);
                textView.setText(R.string.search);
                imageView.setImageResource(R.mipmap.collection_ico);
                break;
            case R.id.radiobutton3: // 生活圈
                viewPager.setCurrentItem(2);
                textView.setText(R.string.life);
                imageView.setImageResource(R.mipmap.smile_ico);
                break;
            case R.id.radiobutton4: // 我的
                viewPager.setCurrentItem(3);
                textView.setText(R.string.about);
                imageView.setImageResource(R.mipmap.me_ico);
                break;
           /* case R.id.radiobutton_add: // 我的
                showAnim();
                break;*/
        }
    }

}
