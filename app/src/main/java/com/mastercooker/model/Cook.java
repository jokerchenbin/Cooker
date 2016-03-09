package com.mastercooker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Cook implements   Parcelable{
    protected int id;//编号,主键
    protected String name;//名称
    protected String food;//食物
    protected String img;//图片
    protected String images;//图片,
    protected String description;//描述
    protected String keywords;//关键字
    protected String message;//资讯内容
    protected int count;//访问次数
    protected int f_count;//收藏数
    protected int r_count;//评论读数

    //实例化静态内部对象CREATOR实现接口Parcelable.Creator
    public static final Parcelable.Creator<Cook> CREATOR = new Creator<Cook>() {

        @Override
        public Cook[] newArray(int size) {
            return new Cook[size];
        }

        //将Parcel对象反序列化为ParcelableDate
        @Override
        public Cook createFromParcel(Parcel source) {
            return new Cook(source);
        }
    };

    public Cook(Parcel parcel){
        id=parcel.readInt();
        count=parcel.readInt();
        f_count=parcel.readInt();
        r_count=parcel.readInt();
        name=parcel.readString();
        food=parcel.readString();
        img=parcel.readString();
        images=parcel.readString();
        description=parcel.readString();
        keywords=parcel.readString();
        message=parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Cook(){}
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeInt(count);
        parcel.writeInt(f_count);
        parcel.writeInt(r_count);
        parcel.writeString(name);
        parcel.writeString(food);
        parcel.writeString(img);
        parcel.writeString(images);
        parcel.writeString(description);
        parcel.writeString(keywords);
        parcel.writeString(message);
    }

    @Override
    public String toString() {
        return "Cook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", food='" + food + '\'' +
                ", img='" + img + '\'' +
                ", images='" + images + '\'' +
                ", description='" + description + '\'' +
                ", keywords='" + keywords + '\'' +
                ", message='" + message + '\'' +
                ", count=" + count +
                ", f_count=" + f_count +
                ", r_count=" + r_count +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getF_count() {
        return f_count;
    }

    public void setF_count(int f_count) {
        this.f_count = f_count;
    }

    public int getR_count() {
        return r_count;
    }

    public void setR_count(int r_count) {
        this.r_count = r_count;
    }
}
