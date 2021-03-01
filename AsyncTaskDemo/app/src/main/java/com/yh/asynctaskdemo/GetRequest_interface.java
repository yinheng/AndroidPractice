package com.yh.asynctaskdemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetRequest_interface {
    @GET
    public Call<ResponseBody> getCall(@Url String url);
}
