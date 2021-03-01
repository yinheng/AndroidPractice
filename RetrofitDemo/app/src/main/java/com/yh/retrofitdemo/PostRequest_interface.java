package com.yh.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostRequest_interface {
    @POST("")
    @FormUrlEncoded
    Call<Translation> getCall(@Field("name") String name, @Field("age") int age);
}
