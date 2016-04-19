package com.mastercooker.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by THB on 2016/4/19.
 */
public class history extends BmobObject{
    protected String userID;
    protected  String history_name;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHistory_name() {
        return history_name;
    }

    public void setHistory_name(String history_name) {
        this.history_name = history_name;
    }
}
