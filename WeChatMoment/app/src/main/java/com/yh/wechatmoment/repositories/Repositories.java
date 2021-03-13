package com.yh.wechatmoment.repositories;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.yh.wechatmoment.model.Comment;
import com.yh.wechatmoment.model.Image;
import com.yh.wechatmoment.model.Sender;
import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.model.User;
import com.yh.wechatmoment.repositories.local.LocalDataSource;
import com.yh.wechatmoment.repositories.remote.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Repositories {
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    public Repositories(Context context) {
        remoteDataSource = new RemoteDataSource();
        localDataSource = new LocalDataSource(context);
    }

    public User getRemoteUserAndSave() {
        User user = localDataSource.getUser();
        if (user != null) {
            localDataSource.deleteUser(user);
        }
        user = remoteDataSource.getUser();
        user.setUserId(UUID.randomUUID().toString());
        localDataSource.saveUser(user);
        return user;
    }

    public User getLocalUser() {
        return localDataSource.getUser();
    }

    public List<Tweet> getRemoteTweetList() {
        return remoteDataSource.getTweetList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveRemoteTweets(List<Tweet> tweets) {
        deleteAllTweets();

        if (tweets == null || tweets.isEmpty()) {
            return;
        }
        tweets = tweets.stream().filter(new Predicate<Tweet>() {
            @Override
            public boolean test(Tweet tweet) {
                return !(tweet.getContent() == null && tweet.getSender() == null && tweet.getComments() == null);
            }
        }).collect(Collectors.toList());
        for (Tweet tweet : tweets) {
            String tweetId = UUID.randomUUID().toString();
            tweet.setTweetId(tweetId);
            localDataSource.saveTweet(tweet);

            List<Image> images = tweet.getImages();
            if (images != null && !images.isEmpty()) {
                for (Image image : images) {
                    image.setTweetId(tweetId);
                    image.setImageId(UUID.randomUUID().toString());
                    localDataSource.saveImage(image);
                }
            }

            Sender tweetSender = tweet.getSender();
            String senderId = UUID.randomUUID().toString();
            tweetSender.setTweetId(tweetId);
            tweetSender.setSenderId(senderId);
            localDataSource.saveSender(tweetSender);

            List<Comment> comments = tweet.getComments();
            if (comments != null && !comments.isEmpty()) {
                for (Comment comment : comments) {
                    String commentId = UUID.randomUUID().toString();
                    comment.setTweetId(tweetId);
                    comment.setCommentId(commentId);
                    localDataSource.saveComment(comment);

                    Sender commentSender = comment.getSender();
                    commentSender.setSenderId(UUID.randomUUID().toString());
                    commentSender.setCommentId(commentId);
                    localDataSource.saveSender(commentSender);
                }
            }
        }
    }

    public List<Tweet> getLocalTweetList() {
        List<Tweet> tweetList = new ArrayList<>();

        for (Tweet tweet : localDataSource.getTweets()) {
            String tweetId = tweet.getTweetId();
            Sender tweetSender = localDataSource.findSenderByTweetId(tweetId);
            tweet.setSender(tweetSender);

            List<Image> images = localDataSource.findImagesByTweetId(tweetId);
            tweet.setImages(images);

            List<Comment> comments = new ArrayList<>();
            for (Comment comment : localDataSource.findCommentsByTweetId(tweetId)) {
                String commentId = comment.getCommentId();
                Sender commentSender = localDataSource.findSenderByCommentId(commentId);
                comment.setSender(commentSender);
                comments.add(comment);
            }

            tweet.setComments(comments);

            tweetList.add(tweet);
        }
        return tweetList;
    }

    public int getTweetsTotalCount() {
        return localDataSource.getTweetsTotalCount();
    }

    public List<Tweet> getLocalTweetListByPage(int start, int pageSize) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Tweet> tweetList = new ArrayList<>();
        List<Tweet> tweetListTest = localDataSource.getTweetsByPage(start, pageSize);
        Log.e("Repositories", String.format("start = %d, pageSize = %d", start, pageSize));
        Log.e("Repositories", "size = " + tweetListTest.size() + " list = " + tweetListTest.toString());
        for (Tweet tweet : localDataSource.getTweetsByPage(start, pageSize)) {
            String tweetId = tweet.getTweetId();
            Sender tweetSender = localDataSource.findSenderByTweetId(tweetId);
            tweet.setSender(tweetSender);

            List<Image> images = localDataSource.findImagesByTweetId(tweetId);
            tweet.setImages(images);

            List<Comment> comments = new ArrayList<>();
            for (Comment comment : localDataSource.findCommentsByTweetId(tweetId)) {
                String commentId = comment.getCommentId();
                Sender commentSender = localDataSource.findSenderByCommentId(commentId);
                comment.setSender(commentSender);
                comments.add(comment);
            }

            tweet.setComments(comments);

            tweetList.add(tweet);
        }
        return tweetList;
    }

    private void deleteAllTweets() {
        localDataSource.deleteTweets();
        localDataSource.deleteComments();
        localDataSource.deleteImages();
        localDataSource.deleteSenders();
    }
}
