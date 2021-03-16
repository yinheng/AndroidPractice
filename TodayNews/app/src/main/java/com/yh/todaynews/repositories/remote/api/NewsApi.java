package com.yh.todaynews.repositories.remote.api;


import com.yh.todaynews.model.News;
import com.yh.todaynews.repositories.remote.model.NewsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApi {
    @GET("index?type=&page=&page_size=&key=34fa60e885110f1c3327237ff46d7d38")
    Call<NewsData> getCall();
}
