package com.yh.wechatmoment.repositories.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yh.wechatmoment.model.Image;

import java.util.List;

@Dao
public interface ImageDao {
    @Query("select * from image")
    List<Image> getImages();

    @Query("select * from image where imageId = :imageId")
    List<Image> findImgByImgId(String imageId);

    @Query("select * from image where tweetId = :tweetId")
    List<Image> findImgByTweetId(String tweetId);

    @Insert
    void insert(Image image);

    @Update
    void update(Image image);

    @Query("delete from image")
    void deleteAll();

}
