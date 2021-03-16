package com.yh.todaynews.repositories.local;

import android.content.Context;

import com.yh.todaynews.model.News;
import com.yh.todaynews.repositories.local.service.NewsService;

import java.util.List;

public class LocalRepositories {
    private NewsService newsService;

    public LocalRepositories(Context context) {
        newsService = new NewsService(context);
    }

    public List<News> getAllNews() {
        return newsService.getNews();
    }

    public List<News> getNews(int start, int pageSize) {
        return newsService.getNews(start, pageSize);
    }

    public int getTotalCount(){
        return newsService.getTotalCount();
    }

    public void insert(News news) {
        newsService.insert(news);
    }

    public void insert(List<News> newsList) {
        if (newsList != null && !newsList.isEmpty()) {
            for (News news : newsList) {
                newsService.insert(news);
            }
        }
    }

    public void delete(News news) {
        newsService.delete(news);
    }

    public void delete(List<News> newsList) {
        if (newsList != null && !newsList.isEmpty()) {
            for (News news : newsList) {
                newsService.delete(news);
            }
        }
    }

    public void deleteAll(){
        newsService.deleteAll();
    }
}
