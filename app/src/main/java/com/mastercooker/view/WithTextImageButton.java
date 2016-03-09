package com.mastercooker.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mastercooker.R;

public class WithTextImageButton extends LinearLayout {

    private ImageView mImageView;
    private TextView mTextView;

    public WithTextImageButton(Context context) {
        this(context, null);
    }

    public WithTextImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WithTextImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        mImageView = new ImageView(context);
        ViewGroup.LayoutParams params =
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WithTextImageButton);
        Drawable drawable = typedArray.getDrawable(R.styleable.WithTextImageButton_src);
        mImageView.setImageDrawable(drawable);
        addView(mImageView,params);

        mTextView = new TextView(context);
        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String text = typedArray.getString(R.styleable.WithTextImageButton_text);
        mTextView.setText(text);
        addView(mTextView, params);

        typedArray.recycle();
       /* View view = LayoutInflater.from(context).inflate(R.layout.item_menu, null);

        mImageView= (ImageView)view.findViewById(R.id.item_iv_tv_iv);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WithTextImageButton);
        Drawable drawable = typedArray.getDrawable(R.styleable.WithTextImageButton_src);
        mImageView.setImageDrawable(drawable);

        mTextView = (TextView)view.findViewById(R.id.item_iv_tv_tv);
        mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        String text = typedArray.getString(R.styleable.WithTextImageButton_text);
        mTextView.setText(text);

        addView(view);
        typedArray.recycle();

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener)
                    onItemClickListener.onItemClick(v);
            }
        });*/
    }

}
