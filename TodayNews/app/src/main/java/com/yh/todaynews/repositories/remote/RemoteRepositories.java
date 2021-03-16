package com.yh.todaynews.repositories.remote;

import com.yh.todaynews.model.News;
import com.yh.todaynews.repositories.remote.service.NewsService;

import java.util.List;

public class RemoteRepositories {
    private NewsService newsService = new NewsService();
    public List<News> getNews(){
        return newsService.getNews();
    }
}
