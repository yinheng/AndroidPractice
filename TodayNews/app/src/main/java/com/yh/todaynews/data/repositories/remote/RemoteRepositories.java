package com.yh.todaynews.data.repositories.remote;

import com.yh.todaynews.data.model.NewsData;
import com.yh.todaynews.data.repositories.remote.service.NewsService;

import java.util.List;

public class RemoteRepositories {
    private NewsService newsService = new NewsService();

    public List<NewsData> getNews() {
        return newsService.getNews();
    }
}
