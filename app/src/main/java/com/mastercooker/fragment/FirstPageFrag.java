package com.mastercooker.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.mastercooker.R;
import com.mastercooker.activity.MessageActivity;
import com.mastercooker.activity.ShowFoodActivity;
import com.mastercooker.adapter.CookInfoAdapter;
import com.mastercooker.model.Cook;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.ToastDiy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class FirstPageFrag extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mSlider;
    private ListView mList;
    private View view;
    private List<Cook> cookList;
    private TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7,textView8;
    private List<TextView> listTextView;


    public FirstPageFrag() {
        super();
    }

    public static FirstPageFrag newInstance() {
        return new FirstPageFrag();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.first_page_frag, container, false);
            initView(view);
        }
        showViewPager();
        getData();
        return view;
    }


    private void getData() {
        FunctionUtils.showLoadingDialog(getActivity());
        BmobQuery<Cook> query = new BmobQuery<>();
        query.setLimit(20);
        query.findObjects(getContext(), new FindListener<Cook>() {
            @Override
            public void onSuccess(List<Cook> list) {
                cookList = list;
                mList.setAdapter(new CookInfoAdapter(getContext(), list));
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                ToastDiy.showShort(getContext(), s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    private void showViewPager() {
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("醋溜豆腐", R.mipmap.food1);
        file_maps.put("彩椒鱿鱼", R.mipmap.food2);
        file_maps.put("佛跳墙", R.mipmap.food3);
        file_maps.put("菠萝鲤鱼", R.mipmap.food4);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
    }

    private void initView(View view) {
        mList = (ListView) view.findViewById(R.id.firstpage_list);
        View v = View.inflate(getContext(), R.layout.head, null);
        textView1= (TextView) v.findViewById(R.id.head_type1);
        textView2= (TextView) v.findViewById(R.id.head_type2);
        textView3= (TextView) v.findViewById(R.id.head_type3);
        textView4= (TextView) v.findViewById(R.id.head_type4);
        textView5= (TextView) v.findViewById(R.id.head_type5);
        textView6= (TextView) v.findViewById(R.id.head_type6);
        textView7= (TextView) v.findViewById(R.id.head_type7);
        textView8= (TextView) v.findViewById(R.id.head_type8);
        listTextView = new ArrayList<>();
        listTextView.add(textView1);
        listTextView.add(textView2);
        listTextView.add(textView3);
        listTextView.add(textView4);
        listTextView.add(textView5);
        listTextView.add(textView6);
        listTextView.add(textView7);
        listTextView.add(textView8);
        showFood();
        mSlider = (SliderLayout) v.findViewById(R.id.slider);
        mList.addHeaderView(v);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("Cook", cookList.get(position - 1));
                startActivity(intent);
            }
        });
    }
    private void showFood(){
        for(int i=0;i<listTextView.size();i++){
            final int num = i;
            listTextView.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(getContext(), ShowFoodActivity.class);
                    intent.putExtra("type", num + 1);
                    intent.putExtra("typeName",listTextView.get(num).getText().toString().trim());
                    startActivity(intent);
                }
            });
        }
    }
}
