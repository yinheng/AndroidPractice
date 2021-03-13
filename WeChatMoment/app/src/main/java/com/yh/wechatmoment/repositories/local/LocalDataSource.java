package com.yh.wechatmoment.repositories.local;

import android.content.Context;

import com.yh.wechatmoment.model.Comment;
import com.yh.wechatmoment.model.Image;
import com.yh.wechatmoment.model.Sender;
import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.model.User;
import com.yh.wechatmoment.repositories.local.service.CommentService;
import com.yh.wechatmoment.repositories.local.service.ImageService;
import com.yh.wechatmoment.repositories.local.service.SenderService;
import com.yh.wechatmoment.repositories.local.service.TweetService;
import com.yh.wechatmoment.repositories.local.service.UserService;

import java.util.List;
import java.util.UUID;

public class LocalDataSource {
    private final TweetService tweetService;
    private final SenderService senderService;
    private final CommentService commentService;
    private final ImageService imageService;
    private final UserService userService;
    public LocalDataSource(Context context){
        tweetService = new TweetService(context);
        senderService = new SenderService(context);
        commentService = new CommentService(context);
        imageService = new ImageService(context);
        userService = new UserService(context);
    }

    public void saveTweet(Tweet tweet){
        tweetService.insert(tweet);
    }

    public void saveSender(Sender sender){
        senderService.insert(sender);
    }

    public void saveComment(Comment comment){
        commentService.insert(comment);
    }

    public void saveImage(Image image){
        imageService.insert(image);
    }

    public List<Tweet> getTweets(){
        return tweetService.findAll();
    }

    public List<Tweet> getTweetsByPage(int start, int pageSize){
        return tweetService.findByPage(start, pageSize);
    }

    public int getTweetsTotalCount(){
        return tweetService.getTotalCount();
    }

    public Sender findSenderByTweetId(String tweetId){
        return senderService.findByTweetId(tweetId);
    }

    public Sender findSenderByCommentId(String commentId){
        return senderService.findByCommentId(commentId);
    }

    public List<Image> findImagesByTweetId(String tweetId){
        return imageService.findImgByTweetId(tweetId);
    }

    public List<Comment> findCommentsByTweetId(String tweetId){
        return commentService.findCommentByTweetId(tweetId);
    }

    public void deleteTweets(){
        tweetService.deleteAll();
    }

    public void deleteComments(){
        commentService.deleteAll();
    }

    public void deleteImages(){
        imageService.deleteAll();
    }

    public void deleteSenders(){
        senderService.deleteAll();
    }

    public void deleteUser(User user){
        userService.delete(user);
    }

    public void saveUser(User user){
        userService.insertUser(user);
    }

    public User getUser(){
        return userService.getUser();
    }
}
