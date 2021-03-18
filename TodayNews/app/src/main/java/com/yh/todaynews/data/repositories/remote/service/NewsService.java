package com.yh.todaynews.data.repositories.remote.service;

import com.yh.todaynews.data.model.NewsData;
import com.yh.todaynews.data.repositories.remote.api.NewsApi;

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

    public List<NewsData> getNews() {
        List<NewsData> newsList = null;
        NewsApi newsApi = retrofit.create(NewsApi.class);
        Call<com.yh.todaynews.data.repositories.remote.model.NewsData> call = newsApi.getCall();
        try {
            Response<com.yh.todaynews.data.repositories.remote.model.NewsData> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                com.yh.todaynews.data.repositories.remote.model.NewsData newsData = response.body();
                if (newsData.getErrorCode() == 0) {
                    newsList = newsData.getResult().getData();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
