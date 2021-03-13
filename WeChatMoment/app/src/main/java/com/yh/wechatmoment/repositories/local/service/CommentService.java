package com.yh.wechatmoment.repositories.local.service;

import android.content.Context;

import com.yh.wechatmoment.model.Comment;
import com.yh.wechatmoment.repositories.local.TweetsDatabase;
import com.yh.wechatmoment.repositories.local.dao.CommentDao;

import java.util.List;

public class CommentService {
    private final CommentDao commentDao;

    public CommentService(Context context) {
        TweetsDatabase tweetsDatabase = TweetsDatabase.getInstance(context);
        this.commentDao = tweetsDatabase.getCommentDao();
    }

    public List<Comment> findCommentByTweetId(String   tweetId) {
        return commentDao.findCommentByTweetId(tweetId);
    }

    public void insert(Comment comment) {
        commentDao.insert(comment);
    }

    public void deleteAll(){
        commentDao.deleteAll();
    }

}
