package com.mastercooker.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mastercooker.R;
import com.mastercooker.tools.ToastDiy;

import java.lang.ref.WeakReference;


/**
 *
 */
public class RadialButtonLayout extends FrameLayout {

    private final static long DURATION_SHORT = 400;
    private WeakReference<Context> weakContext;
    View btnMain;
    View btnBlue;
    View btnIndigo;
    private Context context;
    private boolean isOpen = false;
    private Toast toast;
    private View view;
    private TextView tv_01, tv_02, tv_main;

    /**
     * Default constructor
     *
     * @param context
     */
    public RadialButtonLayout(final Context context) {
        this(context, null);
    }

    public RadialButtonLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        weakContext = new WeakReference<Context>(context);
        view = LayoutInflater.from(context).inflate(R.layout.layout_radial_buttons, this);
        initView();
    }

    /**
     * Created by 陈彬 on 2016/4/18  15:23
     * 方法描述: 初始化组件
     */
    private void initView() {
        tv_01 = (TextView) view.findViewById(R.id.btn_blue);
        tv_02 = (TextView) view.findViewById(R.id.btn_indigo);
        tv_main = (TextView) view.findViewById(R.id.btn_main);
        tv_01.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(v);
            }
        });
        tv_main.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainButtonClicked(v);
                // ToastDiy.showShort(context,"点击");
            }
        });
        tv_02.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(v);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isInEditMode()) {
            //
        } else {

        }
    }

    private void showToast(final int resId) {
        if (toast != null)
            toast.cancel();
        toast = Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onMainButtonClicked(final View btn) {
        int resId = 0;
        if (isOpen) {
            hide(tv_01);
            hide(tv_02);
            isOpen = false;
            resId = R.string.close;
        } else {
            show(tv_01, 4, 250);
            show(tv_02, 5, 250);
            isOpen = true;
            resId = R.string.open;
        }
        showToast(resId);
        btn.playSoundEffect(SoundEffectConstants.CLICK);
    }

    public void onButtonClicked(final View btn) {
        int resId = 0;
        switch (btn.getId()) {

            case R.id.btn_blue:
                resId = R.string.blue;
                break;
            case R.id.btn_indigo:
                resId = R.string.indigo;
                break;
            default:
                resId = R.string.undefined;
        }
        showToast(resId);
        btn.playSoundEffect(SoundEffectConstants.CLICK);
    }

    private final void hide(final View child) {
        child.animate()
                .setDuration(DURATION_SHORT)
                .translationX(0)
                .translationY(0)
                .start();
        //child.setVisibility(View.GONE);
    }

    private final void show(final View child, final int position, final int radius) {
        float angleDeg = 112.f;
        int dist = radius;
        child.setVisibility(View.VISIBLE);
        switch (position) {
            case 1:
                angleDeg += 0.f;
                break;
            case 2:
                angleDeg += 45.f;
                break;
            case 3:
                angleDeg += 90.f;
                break;
            case 4:
                angleDeg += 135.f;
                break;
            case 5:
                angleDeg += 180.f;
                break;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                break;
        }

        final float angleRad = (float) (angleDeg * Math.PI / 180.f);

        final Float x = dist * (float) Math.cos(angleRad);
        final Float y = dist * (float) Math.sin(angleRad);
        child.animate()
                .setDuration(DURATION_SHORT)
                .translationX(x)
                .translationY(y)
                .start();
    }
}