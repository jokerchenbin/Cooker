package com.mastercooker.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.mastercooker.R;
import com.mastercooker.adapter.CookInfoAdapter;
import com.mastercooker.model.Cook;
import com.mastercooker.tools.FunctionUtils;
import com.mastercooker.tools.ToastDiy;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class FirstPageFrag extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout mSlider;
    private ListView mList;
    private View view;


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

    /**
     * Created by 陈彬 on 2016/3/29  14:15
     * 方法描述: 获取数据
     */
    private void getData() {
        FunctionUtils.showLoadingDialog(getActivity());
        BmobQuery<Cook> query = new BmobQuery<>();
        query.setLimit(20);
        query.findObjects(getContext(), new FindListener<Cook>() {
            @Override
            public void onSuccess(List<Cook> list) {
                mList.setAdapter(new CookInfoAdapter(getContext(),list));
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                ToastDiy.showShort(getContext(),s);
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
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.mipmap.hannibal);
        file_maps.put("Big Bang Theory", R.mipmap.bigbang);
        file_maps.put("House of Cards", R.mipmap.house);
        file_maps.put("Game of Thrones", R.mipmap.game_of_thrones);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
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
        mSlider = (SliderLayout) v.findViewById(R.id.slider);
        mList.addHeaderView(v);

    }
}
