package com.yh.todaynews.data.repositories.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yh.todaynews.data.model.NewsData;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("select * from NewsData")
    List<NewsData> getAll();

    @Query("select count(uniqueKey) as total from NewsData")
    int getTotalCount();

    @Query("select * from NewsData limit :start, :pageSize")
    List<NewsData> getByPage(int start, int pageSize);

    @Insert
    void insert(NewsData news);

    @Delete
    void delete(NewsData news);

    @Query("delete from NewsData")
    void deleteAll();
}
