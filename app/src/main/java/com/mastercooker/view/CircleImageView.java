package com.mastercooker.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {

    private Paint mPaint;
    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获得图片资源
        //Bitmap bitmap = ((BitmapDrawable)getDrawable()).getBitmap();
        Bitmap bitmap = null;
        Drawable drawable = getDrawable();
        if(drawable instanceof BitmapDrawable){
            bitmap =((BitmapDrawable)drawable).getBitmap();
        }else if(drawable instanceof ColorDrawable){
            //创建一个图片
            bitmap = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            //创建一个画板
            Canvas c = new Canvas(bitmap);
            //获得ColorDrawable的颜色
            int color = ((ColorDrawable) drawable).getColor();
            //使用该颜色绘图
            c.drawColor(color);
        }
        if(bitmap ==null)return;

        //绘制圆形图片

        //创建一个bitmapShader
        BitmapShader bitmapShader = new BitmapShader(bitmap,BitmapShader.TileMode.CLAMP,BitmapShader.TileMode.CLAMP);
        //为画笔指定shader
        mPaint.setShader(bitmapShader);
        int r = getWidth()/2;
        //绘制图片
        canvas.drawCircle(r,r,r-8,mPaint);
    }
}
