package com.yh.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRequest();
            }
        });
    }
    private void getRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://blog.csdn.net/carson_ho/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_interface request = retrofit.create(GetRequest_interface.class);
        Call<ResponseBody> call = request.getCall("73732076");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.e("result", response.body().string());
                    Log.e("result", "" + response.code());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void postRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostRequest_interface request = retrofit.create(PostRequest_interface.class);
        Call<Translation> call = request.getCall("小明", 15);
        try {
            Response<Translation> response = call.execute();
            Log.e("postResult", response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}