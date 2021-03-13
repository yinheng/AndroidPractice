package com.yh.wechatmoment.repositories.remote;

import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TweetsApi {
    @GET("jsmith")
    Call<User> getUser();

    @GET("jsmith/tweets")
    Call<List<Tweet>> getTweetList();
}
