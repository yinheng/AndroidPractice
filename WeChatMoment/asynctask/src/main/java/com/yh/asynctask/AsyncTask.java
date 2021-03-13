package com.yh.asynctask;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;
import androidx.annotation.WorkerThread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AsyncTask<Param, Result> {
    private final int CORE_POOL_SIZE = 1;
    private final int MAX_POOL_SIZE = 2;
    private final int KEEP_ALIVE_TIME = 2;
    private final int RUNNING = 1;
    private final int COMPLETE = 2;
    private int currentState = COMPLETE;
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>());

    @WorkerThread
    protected abstract Result doInBackground(Param... params);

    @MainThread
    protected void doOnBefore() {
    }

    @MainThread
    protected void doComplete(Result result) {
    }

    @MainThread
    public void execute(Param... params) {
        if (currentState == RUNNING) {
            throw new IllegalStateException("AsyncTask has already running");
        }
        currentState = RUNNING;
        doOnBefore();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = doInBackground(params);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        doComplete(result);
                        currentState = COMPLETE;
                    }
                });
            }
        });
    }

    protected void cancel() {
        threadPoolExecutor.shutdownNow();
    }
}
