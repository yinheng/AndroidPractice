package com.yh.handlerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private final static int[] imgId = new int[]{
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3
    };
    static int currentId = 0;
    private ImageView imageView;

    /**
     * 直接Handler handler = new Handler()这种写法，在Android studio上会提示你如下的警告信息：
     * This Handler class should be static or leaks might occur
     * 意思是这个Handler必须是static的，否则就会引发内存泄露。
     * 在Java中，非静态的匿名InnerClass会持有一个OuterClass的隐式引用，而静态内部类则不会。
     * 如果内部类的生命周期和Activity的生命周期不一致,
     * 则在Activity中要避免使用非静态的内部类，这种情况，就使用一个静态内部类，同时持有一个对Activity的WeakReference。
     */
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> weakReference;

        public MyHandler(MainActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            MainActivity mainActivity = weakReference.get();
            if (mainActivity != null) {
                if (msg.what == 1) {
                    mainActivity.imageView.setImageResource(imgId[currentId]);
                    currentId++;
                }
                if (currentId > 2) {
                    currentId = 0;
                }
            }
        }
    }

    private final Handler myHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = 1;
                myHandler.sendMessage(message);
            }
        }, 0, 1000);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.e("looper", "" + Looper.myLooper());
//                Looper.prepare();
//                Handler handler = new Handler(Looper.myLooper());
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageResource(currentId++);
//                        if(currentId > 2){
//                            currentId = 0;
//                        }
//                        handler.postDelayed(this, 3000);
//                    }
//                }, 2000);
//            }
//        }).start();
        HandlerThread handlerThread = new HandlerThread("Thread1");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        final int[] index = {0};
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e("handler", "hello world"+index[0]);
                handler.postDelayed(this, 1000);
                index[0]++;
                if(index[0] > 9){
                    handler.removeCallbacks(this);
                }
            }
        });

        Log.d("Yinheng", "Looper.myLooper()=" + Looper.myLooper());
    }
}