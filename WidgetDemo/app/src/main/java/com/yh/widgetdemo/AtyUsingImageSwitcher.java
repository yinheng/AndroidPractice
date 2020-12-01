package com.yh.widgetdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AtyUsingImageSwitcher extends AppCompatActivity {
    private ImageSwitcher imageSwitcher;
    private List<Integer> imgList = new ArrayList<>();
    private int index;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_imageswitcher);
        imageSwitcher = findViewById(R.id.imgSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(AtyUsingImageSwitcher.this);
                return imageView;
            }
        });
        imgList.add(R.mipmap.img1);
        imgList.add(R.mipmap.img2);
        imgList.add(R.mipmap.img3);
        index = 0;
        imageSwitcher.setImageResource(imgList.get(index));
        switchImg();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();

    }

    private void switchImg() {
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    index++;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageSwitcher.setImageResource(imgList.get(index % 3));
                        }
                    });
                }
            };
            timer.scheduleAtFixedRate(timerTask, 1000, 200);
        }
    }

    private void stopTimer() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
