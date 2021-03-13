package com.yh.wechatmoment.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Image {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String imageId;
    private String tweetId;
    private String url;

    public Image(String url) {
        this.url = url;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTweetId() {
        return tweetId;
    }

    public void setTweetId(String tweetId) {
        this.tweetId = tweetId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "imageId=" + imageId +
                ", tweetId=" + tweetId +
                ", url='" + url + '\'' +
                '}';
    }
}
