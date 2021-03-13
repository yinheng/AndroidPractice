package com.yh.wechatmoment.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Sender {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String senderId;
    private String avatar;
    private String nick;
    @SerializedName("username")
    private String userName;
    private String tweetId;
    private String commentId;

    public Sender() {
        senderId = null;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "senderId=" + senderId +
                ", avatar='" + avatar + '\'' +
                ", nick='" + nick + '\'' +
                ", userName='" + userName + '\'' +
                ", tweetId=" + tweetId +
                ", commentId=" + commentId +
                '}';
    }
}
