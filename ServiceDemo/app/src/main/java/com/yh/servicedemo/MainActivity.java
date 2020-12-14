package com.yh.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Button btnCurrentNum;
    private Intent intent;
    boolean isConnect = false;
    private MyService myService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStartService = findViewById(R.id.btnStartService);
        Button btnStopService = findViewById(R.id.btnStopService);
        Button btnBindService = findViewById(R.id.bindService);
        Button btnUnbindService = findViewById(R.id.unBindService);
        btnCurrentNum = findViewById(R.id.btnCurrentNum);
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
        btnBindService.setOnClickListener(this);
        btnUnbindService.setOnClickListener(this);
        btnCurrentNum.setOnClickListener(this);
        intent = new Intent(this, MyService.class);
        intent.putExtra("text", "hello android");

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStartService:
                startService(intent);
                break;
            case R.id.btnStopService:
                stopService(intent);
                break;
            case R.id.bindService:
                isConnect = bindService(intent, this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.unBindService:
                if(isConnect){
                    unbindService(this);
                    isConnect = false;
                    myService = null;
                    btnCurrentNum.setText("current num");
                }
                break;
            case R.id.btnCurrentNum:
                if(myService != null){
                    btnCurrentNum.setText("current num: " + myService.getCurrentNum());
                    Log.e("current num", myService.getCurrentNum()+"");
                }
                break;
        }

    }
    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        Log.e("MainActivity", "onServiceConnected");
        myService = ((MyService.MyBinder) binder).getMyService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.e("MainActivity", "onServiceDisconnected");

    }
}