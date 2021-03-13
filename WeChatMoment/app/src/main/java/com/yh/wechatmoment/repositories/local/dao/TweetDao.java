package com.yh.wechatmoment.repositories.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.yh.wechatmoment.model.Tweet;

import java.util.List;

@Dao
public interface TweetDao {
    @Query("select * from tweet where tweetId = :tweetId")
    Tweet findByTweetId(String tweetId);

    @Query("select count(tweetId) as count from tweet")
    int findTotalCount();

    @Query("select * from tweet")
    List<Tweet> findAll();

    @Query("select * from tweet limit :start , :pageSize")
    List<Tweet> findByPage(int start, int pageSize);

    @Insert
    void insert(Tweet tweet);

    @Query("delete from tweet")
    void deleteAll();

}
