package com.yh.wechatmoment.repositories.local.service;

import android.content.Context;

import com.yh.wechatmoment.model.Sender;
import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.repositories.local.TweetsDatabase;
import com.yh.wechatmoment.repositories.local.dao.SenderDao;
import com.yh.wechatmoment.repositories.local.dao.TweetDao;

import java.util.List;

public class SenderService {
    private final SenderDao senderDao;

    public SenderService(Context context) {
        TweetsDatabase tweetsDatabase = TweetsDatabase.getInstance(context);
        this.senderDao = tweetsDatabase.getSenderDao();
    }

    public Sender findByTweetId(String tweetId) {
        return senderDao.findByTweetId(tweetId);
    }
    public Sender findByCommentId(String commentId) {
        return senderDao.findByCommentId(commentId);
    }

    public void insert(Sender sender) {
        senderDao.insert(sender);
    }

    public void deleteAll(){
        senderDao.deleteAll();
    }
}
