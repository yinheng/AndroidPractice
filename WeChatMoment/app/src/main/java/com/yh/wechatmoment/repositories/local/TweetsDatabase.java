package com.yh.wechatmoment.repositories.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yh.wechatmoment.model.Comment;
import com.yh.wechatmoment.model.Image;
import com.yh.wechatmoment.model.Sender;
import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.model.User;
import com.yh.wechatmoment.repositories.local.dao.CommentDao;
import com.yh.wechatmoment.repositories.local.dao.ImageDao;
import com.yh.wechatmoment.repositories.local.dao.SenderDao;
import com.yh.wechatmoment.repositories.local.dao.TweetDao;
import com.yh.wechatmoment.repositories.local.dao.UserDao;

@Database(entities = {User.class, Image.class, Comment.class, Sender.class, Tweet.class}, version = 1, exportSchema = false)
public abstract class TweetsDatabase extends RoomDatabase {
    private static TweetsDatabase create(Context context){
        String DB_NAME = "TweetDatabase.db";
        return Room.databaseBuilder(context, TweetsDatabase.class, DB_NAME).build();
    }

    public static TweetsDatabase getInstance(Context context){
        return create(context);
    }

    public abstract UserDao getUserDao();
    public abstract ImageDao getImageDao();
    public abstract CommentDao getCommentDao();
    public abstract SenderDao getSenderDao();
    public abstract TweetDao getTweetDao();

}
