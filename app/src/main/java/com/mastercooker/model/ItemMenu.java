package com.mastercooker.model;

public class ItemMenu {
    private String name;
    private int before_click_src;
    private int after_click_src;

    public String getName() {
        return name;
    }

    public ItemMenu(String name, int before_click_src, int after_click_src) {
        this.name = name;
        this.before_click_src = before_click_src;
        this.after_click_src = after_click_src;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBefore_click_src() {
        return before_click_src;
    }

    public void setBefore_click_src(int before_click_src) {
        this.before_click_src = before_click_src;
    }

    public int getAfter_click_src() {
        return after_click_src;
    }

    public void setAfter_click_src(int after_click_src) {
        this.after_click_src = after_click_src;
    }
}
