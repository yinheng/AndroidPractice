package com.yh.retrofitdemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetRequest_interface {

    @GET("/article/details/{index}")
    Call<ResponseBody> getCall(@Path("index") String index);
}
