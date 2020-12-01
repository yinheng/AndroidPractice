package com.yh.widgetdemo;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class AtyUsingProgressBar extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_progressbar);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setMax(100);
        progressBar.setProgress(50);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTimer();
    }

    private Timer timer;
    private TimerTask timerTask;
    private int index = 0;

    private void startTimer() {
        if (timer == null) {
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    index++;
                    progressBar.setProgress(index);
                }
            };
        }
        timer.scheduleAtFixedRate(timerTask, 100, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timerTask.cancel();
        }
    }
}
