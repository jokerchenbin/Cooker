package com.mastercooker.model;

public class CookLog extends Cook{

    private String  is_love = "false";
    private String  is_hate ;
    private int look_times;
    private String  one;
    private String two;
    private String three;

    public String getIs_love() {
        return is_love;
    }

    public void setIs_love(String is_love) {
        this.is_love = is_love;
    }

    public String getIs_hate() {
        return is_hate;
    }

    public void setIs_hate(String is_hate) {
        this.is_hate = is_hate;
    }

    public int getLook_times() {
        return look_times;
    }

    public void setLook_times(int look_times) {
        this.look_times = look_times;
    }

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    public String getThree() {
        return three;
    }

    public void setThree(String three) {
        this.three = three;
    }
}
