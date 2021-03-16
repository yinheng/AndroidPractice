package com.yh.todaynews.repositories.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yh.todaynews.model.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("select * from news")
    List<News> getAll();

    @Query("select count(uniqueKey) as total from news")
    int getTotalCount();

    @Query("select * from news limit :start, :pageSize")
    List<News> getByPage(int start, int pageSize);

    @Insert
    void insert(News news);

    @Delete
    void delete(News news);
    @Query("delete from news")
    void deleteAll();
}
