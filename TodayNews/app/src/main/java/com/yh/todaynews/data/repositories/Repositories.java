package com.yh.todaynews.data.repositories;

import android.content.Context;

import com.yh.todaynews.data.model.NewsData;
import com.yh.todaynews.data.repositories.local.LocalRepositories;
import com.yh.todaynews.data.repositories.remote.RemoteRepositories;
import com.yh.todaynews.ui.news.model.News;
import com.yh.todaynews.ui.news.model.NewsMapper;

import java.util.List;

public class Repositories {
    private final RemoteRepositories remoteRepositories;
    private final LocalRepositories localRepositories;

    public Repositories(Context context) {
        remoteRepositories = new RemoteRepositories();
        localRepositories = new LocalRepositories(context);
    }

    public List<News> getRemoteNews() {
        List<NewsData> newsList = remoteRepositories.getNews();
        deleteAll();
        localRepositories.insert(newsList);
        return new NewsMapper().toNewsList(newsList);
    }

    public List<News> getLocalNewsByPage(int start, int pageSize) {
        return new NewsMapper().toNewsList(localRepositories.getNews(start, pageSize));
    }

    public int getLocalTotalCount() {
        return localRepositories.getTotalCount();
    }

    public List<NewsData> getLocalNews() {
        return localRepositories.getAllNews();
    }

    public void deleteAll() {
        localRepositories.deleteAll();
    }
}
