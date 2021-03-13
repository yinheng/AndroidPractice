package com.yh.wechatmoment.repositories.remote;

import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.model.User;

import java.util.List;

public class RemoteDataSource {
    private TweetsService tweetsService = new TweetsService();
    public User getUser(){
        return tweetsService.getUser();
    }

    public List<Tweet> getTweetList(){
        return tweetsService.getTweetList();
    }
}
