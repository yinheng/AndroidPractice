package com.yh.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private UserManagerAidl userManagerAidl;
    private EditText name;
    private EditText age;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnGetName:
                    try {
                        Toast.makeText(MainActivity.this, userManagerAidl.getString(), Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.addUser:
                    try {
                        String nameStr = name.getText().toString();
                        int ageStr = Integer.parseInt(age.getText().toString());
                        userManagerAidl.addUser(new User(nameStr, ageStr));
                        Toast.makeText(MainActivity.this, "add user success", Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.getUsers:
                    try {
                        Toast.makeText(MainActivity.this, userManagerAidl.getUsers().toString(), Toast.LENGTH_SHORT).show();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);

        Intent intent = new Intent(this, MyService.class);

        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                userManagerAidl = UserManagerAidl.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);

        findViewById(R.id.btnGetName).setOnClickListener(clickListener);
        findViewById(R.id.addUser).setOnClickListener(clickListener);
        findViewById(R.id.getUsers).setOnClickListener(clickListener);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserListActivity.class);
                startActivity(intent);
            }
        });
    }
}