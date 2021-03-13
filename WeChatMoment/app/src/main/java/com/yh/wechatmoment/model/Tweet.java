package com.yh.wechatmoment.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.List;

@Entity
public class Tweet {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String tweetId;
    private String content;
    @Ignore
    private List<Image> images;
    @Ignore
    private Sender sender;
    @Ignore
    private List<Comment> comments;

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
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
                "tweetId=" + tweetId +
                ", content='" + content + '\'' +
                ", images=" + images +
                ", sender=" + sender +
                ", comments=" + comments +
                '}';
    }
}
