package com.yh.wechatmoment.repositories.local.service;

import android.content.Context;

import com.yh.wechatmoment.model.User;
import com.yh.wechatmoment.repositories.local.TweetsDatabase;
import com.yh.wechatmoment.repositories.local.dao.UserDao;

public class UserService {
    private final UserDao userDao;

    public UserService(Context context) {
        TweetsDatabase tweetsDatabase = TweetsDatabase.getInstance(context);
        this.userDao = tweetsDatabase.getUserDao();
    }

    public User getUser() {
        return userDao.getUser();
    }

    public void insertUser(User user){
        userDao.insert(user);
    }
    public void delete(User user){
        userDao.delete(user);
    }

}
