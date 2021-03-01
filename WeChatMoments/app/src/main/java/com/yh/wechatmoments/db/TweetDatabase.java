package com.yh.wechatmoments.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yh.wechatmoments.model.Tweet;
import com.yh.wechatmoments.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class TweetDatabase extends RoomDatabase {
    private static TweetDatabase tweetDatabase;
    private static final String DB_NAME = "TweetDatabase.db";

    private static TweetDatabase create(Context context){
        return Room.databaseBuilder(context, TweetDatabase.class, DB_NAME)
                .build();
    }

    public static synchronized TweetDatabase getInstance(Context context){
        if(tweetDatabase == null){
            tweetDatabase = create(context);
        }
        return tweetDatabase;
    }

    public abstract UserDao getUserDao();
}
