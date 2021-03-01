package com.yh.aidldemo2;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yh.aidldemo.UserManagerAidl;

public class MainActivity extends AppCompatActivity {
    private UserManagerAidl userManagerAidl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setAction("com.yh.aidldemo.MyService");
        intent.setPackage("com.yh.aidldemo");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                userManagerAidl = UserManagerAidl.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);

        findViewById(R.id.btnGetUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(MainActivity.this, userManagerAidl.getUsers().toString(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}