package com.yh.okhttpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OkHttpUtil okHttpUtil = OkHttpUtil.getInstance();
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.tv);

        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String getStr = okHttpUtil.get("http://www.baidu.com");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(getStr);
                            }
                        });
                    }
                }).start();
            }
        });
        findViewById(R.id.btnPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postResult = okHttpUtil.post("", "");
            }
        });
    }
}