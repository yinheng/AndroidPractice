package com.yh.todaynews.repositories.local.service;

import android.content.Context;

import com.yh.todaynews.model.News;
import com.yh.todaynews.repositories.local.NewsDatabase;
import com.yh.todaynews.repositories.local.dao.NewsDao;

import java.util.List;

public class NewsService {
    private final NewsDao newsDao;
    public NewsService(Context context){
        NewsDatabase newsDatabase = NewsDatabase.getInstance(context);
        newsDao = newsDatabase.getNewsDao();
    }

    public List<News> getNews(){
        return newsDao.getAll();
    }

    public List<News> getNews(int start, int pageSize){
        return newsDao.getByPage(start, pageSize);
    }

    public int getTotalCount(){
        return newsDao.getTotalCount();
    }

    public void insert(News news){
        newsDao.insert(news);
    }

    public void delete(News news){
        newsDao.delete(news);
    }

    public void deleteAll(){
        newsDao.deleteAll();;
    }
}
