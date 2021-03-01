package com.yh.wechatmoments.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("profile-image")
    private String profileImage;
    private String avatar;
    @SerializedName("username")
    private String userName;
    private String nick;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", profileImage='" + profileImage + '\'' +
                ", avatar='" + avatar + '\'' +
                ", userName='" + userName + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }
}
