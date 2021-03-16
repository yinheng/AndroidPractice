package com.yh.todaynews.repositories.remote.service;

import com.yh.todaynews.model.News;
import com.yh.todaynews.repositories.remote.api.NewsApi;
import com.yh.todaynews.repositories.remote.model.NewsData;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsService {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://v.juhe.cn/toutiao/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public List<News> getNews(){
        List<News> newsList = null;
        NewsApi newsApi = retrofit.create(NewsApi.class);
        Call<NewsData> call = newsApi.getCall();
        try {
            Response<NewsData> response = call.execute();
            if(response.isSuccessful() && response.body() != null){
                NewsData newsData = response.body();
                if(newsData.getErrorCode() == 0){
                    newsList = newsData.getResult().getData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
