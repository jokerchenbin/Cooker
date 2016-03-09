package com.mastercooker.model;


import android.os.Parcel;
import android.os.Parcelable;

public class FoodStyle implements Parcelable {
    //实例化静态内部对象CREATOR实现接口Parcelable.Creator
    public static final Parcelable.Creator<FoodStyle> CREATOR = new Creator<FoodStyle>() {

        @Override
        public FoodStyle[] newArray(int size) {
            return new FoodStyle[size];
        }

        //将Parcel对象反序列化为ParcelableDate
        @Override
        public FoodStyle createFromParcel(Parcel source) {
            return new FoodStyle(source);
        }
    };
    private int image_name_id;
    private String describe;
    private int id;

    public FoodStyle(Parcel parcel) {
        this.image_name_id = parcel.readInt();
        this.describe = parcel.readString();
        this.id = parcel.readInt();
    }

    public FoodStyle(int image_name_id, String describe, int id) {
        this.image_name_id = image_name_id;
        this.describe = describe;
        this.id = id;
    }

    public FoodStyle() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image_name_id);
        dest.writeString(describe);
        dest.writeInt(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage_name_id() {
        return image_name_id;
    }

    public void setImage_name_id(int image_name_id) {
        this.image_name_id = image_name_id;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
