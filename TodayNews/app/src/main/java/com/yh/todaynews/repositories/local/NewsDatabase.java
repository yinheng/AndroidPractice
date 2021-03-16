package com.yh.todaynews.repositories.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.yh.todaynews.model.News;
import com.yh.todaynews.repositories.local.dao.NewsDao;

@Database(entities = {News.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "newsDatabase";
    private static NewsDatabase newsDatabase;

    private static NewsDatabase create(Context context) {
        return Room.databaseBuilder(context, NewsDatabase.class, DATABASE_NAME).build();
    }

    public static NewsDatabase getInstance(Context context) {
        if (newsDatabase == null) {
            newsDatabase = NewsDatabase.create(context);
        }
        return newsDatabase;
    }

    public abstract NewsDao getNewsDao();
}
