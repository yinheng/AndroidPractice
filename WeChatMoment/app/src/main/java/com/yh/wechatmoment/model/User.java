package com.yh.wechatmoment.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class User {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String userId;
    @SerializedName("profile-image")
    private String profileImage;
    private String nick;
    @SerializedName("username")
    private String userName;
    private String avatar;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", profileImage='" + profileImage + '\'' +
                ", nick='" + nick + '\'' +
                ", userName='" + userName + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
