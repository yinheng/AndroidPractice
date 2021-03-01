package com.yh.wechatmoments.model;

import com.google.gson.annotations.SerializedName;

public class Sender {
    @SerializedName("username")
    private String userName;
    private String nick;
    private String avatar;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Sender{" +
                "userName='" + userName + '\'' +
                ", nick='" + nick + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
