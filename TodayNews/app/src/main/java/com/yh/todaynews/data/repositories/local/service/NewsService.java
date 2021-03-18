package com.yh.todaynews.data.repositories.local.service;

import android.content.Context;

import com.yh.todaynews.data.model.NewsData;
import com.yh.todaynews.data.repositories.local.NewsDatabase;
import com.yh.todaynews.data.repositories.local.dao.NewsDao;

import java.util.List;

public class NewsService {
    private final NewsDao newsDao;

    public NewsService(Context context) {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(context);
        newsDao = newsDatabase.getNewsDao();
    }

    public List<NewsData> getNews() {
        return newsDao.getAll();
    }

    public List<NewsData> getNews(int start, int pageSize) {
        return newsDao.getByPage(start, pageSize);
    }

    public int getTotalCount() {
        return newsDao.getTotalCount();
    }

    public void insert(NewsData news) {
        newsDao.insert(news);
    }

    public void delete(NewsData news) {
        newsDao.delete(news);
    }

    public void deleteAll() {
        newsDao.deleteAll();
        ;
    }
}
