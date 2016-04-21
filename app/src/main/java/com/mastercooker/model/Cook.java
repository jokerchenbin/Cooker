package com.mastercooker.model;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Cook extends BmobObject{
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
    protected  int type;
    protected String postName;

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    private BmobFile file;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }
}
