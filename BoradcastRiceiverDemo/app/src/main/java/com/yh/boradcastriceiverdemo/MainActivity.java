package com.yh.boradcastriceiverdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSendBroadcast = findViewById(R.id.btnSendBroadcast);
        btnSendBroadcast.setOnClickListener(this);
//        intent = new Intent(this, MyBroadcastReceiver.class);
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        intent = new Intent(myBroadcastReceiver.ACTION);
        intent.putExtra("text", "hello android");

        findViewById(R.id.btnRegBc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerReceiver(myBroadcastReceiver, new IntentFilter(myBroadcastReceiver.ACTION));
            }
        });
        findViewById(R.id.btnUnRegBc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(myBroadcastReceiver);
            }
        });

        findViewById(R.id.btnCP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, AtyUsingContentProvider.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        sendBroadcast(intent);
    }
}