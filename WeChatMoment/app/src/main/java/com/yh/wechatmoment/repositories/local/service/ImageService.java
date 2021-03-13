package com.yh.wechatmoment.repositories.local.service;

import android.content.Context;

import com.yh.wechatmoment.model.Image;
import com.yh.wechatmoment.repositories.local.TweetsDatabase;
import com.yh.wechatmoment.repositories.local.dao.ImageDao;

import java.util.List;

public class ImageService {
    private final ImageDao imageDao;

    public ImageService(Context context) {
        TweetsDatabase tweetsDatabase = TweetsDatabase.getInstance(context);
        this.imageDao = tweetsDatabase.getImageDao();
    }

    public List<Image> findImgByTweetId(String tweetId) {
        return imageDao.findImgByTweetId(tweetId);
    }

    public void insert(Image image) {
        imageDao.insert(image);
    }

    public void deleteAll(){
        imageDao.deleteAll();
    }
}
