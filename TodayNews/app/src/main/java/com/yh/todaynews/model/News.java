package com.yh.todaynews.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class News {
    @SerializedName("uniquekey")
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String uniqueKey;
    private String title;
    private String date;
    @SerializedName("author_name")
    private String author;
    private String category;
    private String url;
    @SerializedName("thumbnail_pic_s")
    private String thumbnail1;
    @SerializedName("thumbnail_pic_s02")
    private String thumbnail2;
    @SerializedName("thumbnail_pic_s03")
    private String thumbnail3;
    @SerializedName("is_content")
    private String isContent;

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail1() {
        return thumbnail1;
    }

    public void setThumbnail1(String thumbnail1) {
        this.thumbnail1 = thumbnail1;
    }

    public String getThumbnail2() {
        return thumbnail2;
    }

    public void setThumbnail2(String thumbnail2) {
        this.thumbnail2 = thumbnail2;
    }

    public String getThumbnail3() {
        return thumbnail3;
    }

    public void setThumbnail3(String thumbnail3) {
        this.thumbnail3 = thumbnail3;
    }

    public String getIsContent() {
        return isContent;
    }

    public void setIsContent(String isContent) {
        this.isContent = isContent;
    }
}
