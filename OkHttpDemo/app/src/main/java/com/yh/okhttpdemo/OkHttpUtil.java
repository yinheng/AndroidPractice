package com.yh.okhttpdemo;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {

    private static OkHttpUtil okHttpUtil;

    private OkHttpUtil() {
    }

    public static OkHttpUtil getInstance() {
        if (okHttpUtil == null) {
            okHttpUtil = new OkHttpUtil();
        }
        return okHttpUtil;
    }

    public String get(String url) {
        String responseStr = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.e("responseCode: ", response.code() + "");
            if (response.isSuccessful()) {
                responseStr = response.body().string();
            }
        } catch (IOException e) {
            Log.e("get", e.getMessage());
            e.printStackTrace();
        }
        return responseStr;
    }

    public String post(String url, String data) {
        String result = "";
        OkHttpClient client = new OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        Log.e("response", response.body().string());
                        return response;
                    }
                })
                .build();
        RequestBody requestBody = RequestBody.create(data, MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            Log.e("responseCode: ", response.code() + "");
            if (response.isSuccessful()) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
