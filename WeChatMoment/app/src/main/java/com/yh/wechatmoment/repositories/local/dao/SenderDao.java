package com.yh.wechatmoment.repositories.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yh.wechatmoment.model.Sender;

@Dao
public interface SenderDao {
    @Query("select * from sender where tweetId = :tweetId")
    Sender findByTweetId(String  tweetId);

    @Query("select * from sender where commentId = :commentId")
    Sender findByCommentId(String  commentId);

    @Insert
    void insert(Sender sender);

    @Query("delete from sender")
    void deleteAll();
}
