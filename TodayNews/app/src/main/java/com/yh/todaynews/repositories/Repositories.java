package com.yh.todaynews.repositories;

import android.content.Context;

import com.yh.todaynews.model.News;
import com.yh.todaynews.repositories.local.LocalRepositories;
import com.yh.todaynews.repositories.remote.RemoteRepositories;

import java.util.List;

public class Repositories {
    private final RemoteRepositories remoteRepositories;
    private final LocalRepositories localRepositories;
    public Repositories(Context context){
        remoteRepositories = new RemoteRepositories();
        localRepositories = new LocalRepositories(context);
    }
    public List<News> getRemoteNews(){
        List<News> newsList = remoteRepositories.getNews();

        deleteAll();
        localRepositories.insert(newsList);
        return newsList;
    }

    public List<News> getLocalNewsByPage(int start, int pageSize){
        return localRepositories.getNews(start, pageSize);
    }

    public int getLocalTotalCount(){
        return localRepositories.getTotalCount();
    }

    public List<News> getLocalNews(){
        return localRepositories.getAllNews();
    }

    public void deleteAll(){
        localRepositories.deleteAll();
    }
}
