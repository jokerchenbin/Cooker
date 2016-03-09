package com.mastercooker.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Util {

    public  static  String getZero(int n){
        StringBuffer buffer = new StringBuffer("a");
        for(int i = 4;i>Integer.toString(n).length();i--){
            buffer.append("0");
        }
        buffer.append(n);
        return buffer.toString();
    }

    public static String[] getCookLogColumns() {
        String[] columns = new String[]{CookStore.IS_LOVE, CookStore.IS_HATE, CookStore.LOOK_TIMES
                , CookStore.ONE, CookStore.TWO, CookStore.THREE, CookStore.ID
                , CookStore.NAME, CookStore.FOOD, CookStore.IMG, CookStore.IMAGES
                , CookStore.DESCRIPTION, CookStore.KEYWORDS, CookStore.MESSAGE, CookStore.COUNT
                , CookStore.F_COUNT, CookStore.R_COUNT};

        return columns;
    }

    public static String[] getCookColumns() {
        String[] columns = new String[]{CookStore.ID
                , CookStore.NAME, CookStore.FOOD, CookStore.IMG, CookStore.IMAGES
                , CookStore.DESCRIPTION, CookStore.KEYWORDS, CookStore.MESSAGE, CookStore.COUNT
                , CookStore.F_COUNT, CookStore.R_COUNT};

        return columns;
    }
}
