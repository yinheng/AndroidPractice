package com.yh.todaynews.data.repositories.local;

import android.content.Context;

import com.yh.todaynews.data.model.NewsData;
import com.yh.todaynews.data.repositories.local.service.NewsService;

import java.util.List;

public class LocalRepositories {
    private NewsService newsService;

    public LocalRepositories(Context context) {
        newsService = new NewsService(context);
    }

    public List<NewsData> getAllNews() {
        return newsService.getNews();
    }

    public List<NewsData> getNews(int start, int pageSize) {
        return newsService.getNews(start, pageSize);
    }

    public int getTotalCount() {
        return newsService.getTotalCount();
    }

    public void insert(NewsData news) {
        newsService.insert(news);
    }

    public void insert(List<NewsData> newsList) {
        if (newsList != null && !newsList.isEmpty()) {
            for (NewsData news : newsList) {
                newsService.insert(news);
            }
        }
    }

    public void delete(NewsData news) {
        newsService.delete(news);
    }

    public void delete(List<NewsData> newsList) {
        if (newsList != null && !newsList.isEmpty()) {
            for (NewsData news : newsList) {
                newsService.delete(news);
            }
        }
    }

    public void deleteAll() {
        newsService.deleteAll();
    }
}
