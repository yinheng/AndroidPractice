package com.yh.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String text = intent.getStringExtra("text");
        Log.e("MyService", "onBind");
        Log.e("text", text);
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTimer();
        Log.e("MyService", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
        Log.e("MyService", "onDestroy");
    }

    class MyBinder extends Binder{

        public MyService getMyService() {
            return MyService.this;
        }
    }

    public int getCurrentNum(){
        return i;
    }

    private Timer timer = null;
    private TimerTask timerTask;
    private int i;
    private void startTimer(){
        if(timer == null){
            i = 0;
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    i ++;
                    Log.e("current num", ""+i);
                }
            };
            timer.schedule(timerTask, 1000, 1000);
        }
    }
    private void stopTimer(){
        if(timerTask != null){
            timerTask.cancel();
        }
        if(timer != null){
            timer.cancel();
        }
        timerTask = null;
        timer = null;
    }
}
