package com.yh.wechatmoments.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tweet {
    private int id;

    private String content;
    @SerializedName("images")
    private List<Image> imgList;
    private Sender sender;
    private List<Comment> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Image> getImgList() {
        return imgList;
    }

    public void setImgList(List<Image> imgList) {
        this.imgList = imgList;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "content='" + content + '\'' +
                ", imgList=" + imgList +
                ", sender=" + sender +
                ", comments=" + comments +
                '}';
    }
}
