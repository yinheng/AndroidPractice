package com.yh.wechatmoment.repositories.local.service;

import android.content.Context;

import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.repositories.local.TweetsDatabase;
import com.yh.wechatmoment.repositories.local.dao.TweetDao;

import java.util.List;

public class TweetService {
    private final TweetDao tweetDao;

    public TweetService(Context context) {
        TweetsDatabase tweetsDatabase = TweetsDatabase.getInstance(context);
        this.tweetDao = tweetsDatabase.getTweetDao();
    }

    public List<Tweet> findAll() {
        return tweetDao.findAll();
    }

    public List<Tweet> findByPage(int start, int pageSize) {
        return tweetDao.findByPage(start, pageSize);
    }

    public int getTotalCount(){
        return tweetDao.findTotalCount();
    }

    public void insert(Tweet tweet) {
        tweetDao.insert(tweet);
    }

    public void deleteAll(){
        tweetDao.deleteAll();
    }
}
