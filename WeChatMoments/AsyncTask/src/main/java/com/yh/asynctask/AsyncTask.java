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
    private final int MAX_POOL_SIZE = 4;
    private final long KEEP_ALIVE_TIME = 1;
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>());
    private final int RUNNING = 1;
    private final int COMPLETE = 2;
    private int state = COMPLETE;

    @MainThread
    protected void beforeExecute() {
    }

    @WorkerThread
    protected abstract Result doInBackground(Param... params);

    @MainThread
    protected void completeExecute(Result result) {
    }

    public void execute(Param... params) {
        if (state == RUNNING) {
            throw new IllegalStateException("asyncTask has task already running");
        }

        state = RUNNING;
        beforeExecute();
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = doInBackground(params);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        completeExecute(result);
                        state = COMPLETE;
                    }
                });
            }
        });
    }

    protected void cancel() {
        if (state == RUNNING) {
            threadPoolExecutor.shutdownNow();
            state = COMPLETE;
        }
    }
}
