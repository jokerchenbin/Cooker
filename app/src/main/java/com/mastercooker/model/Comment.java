package com.mastercooker.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by THB on 2016/4/22.
 */
public class Comment extends BmobObject{
    protected String postID;
    protected  String commentUser;
    protected  String content;

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(String commentUser) {
        this.commentUser = commentUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
