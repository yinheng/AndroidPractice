package com.yh.asynctaskdemo;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AsyncTaskTest<Param, Process, Result> {
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4,
            2,
            2,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r);
                }
            },
            new ThreadPoolExecutor.AbortPolicy()
    );

    public AsyncTaskTest() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void execute(Param... params) {
        onPreExecute();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = doInBackground(params);
                Log.e("doInBackground", "finish");
                // 任务完成
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        postExecute(result);
                    }
                });
            }
        });

        List<String> names = new ArrayList<>();

        names.stream().filter(new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.equals("ZhangSan");
            }
        }).collect(Collectors.toList());



        List<Integer> codes = new ArrayList<>();
        for (String name : names) {
            codes.add(name.hashCode());
        }

        codes = names.stream().map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.hashCode();
            }
        }).collect(Collectors.toList());

    }

    public abstract Result doInBackground(Param... params);

    public void onPreExecute() {
    }

    public void publishProcess(Process process) {
    }

    public abstract void processUpdate();

    public void postExecute(Result result) {
    }
}
