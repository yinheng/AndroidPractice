package com.yh.wechatmoments.retrofit;

import com.yh.wechatmoments.model.Tweet;
import com.yh.wechatmoments.model.User;

import java.util.List;

import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetRequest_interface {

    @GET
    public Call<Response> getCall(@Url String url);

    @GET("jsmith")
    Call<User> getUser();

    @GET("jsmith/tweets")
    Call<List<Tweet>> getTweetsCall();
}
