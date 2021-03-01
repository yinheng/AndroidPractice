package com.yh.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private UserManagerAidl userManagerAidl;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        Intent intent = new Intent(this, MyService.class);
        Log.e("MyUserListActivity", "onCreate");

        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                userManagerAidl = UserManagerAidl.Stub.asInterface(service);

                Log.e("MyUserListAty", "onServiceConnected ");
                try {
                    recyclerView = findViewById(R.id.list);
                    Log.e("MyUserListAty", "userManagerAidl is" + userManagerAidl);
                    List<User> userList = (userManagerAidl == null) ? new ArrayList<>() : userManagerAidl.getUsers();
                    Log.e("MyUserListAty", "userList is" + userList);
                    recyclerView.setAdapter(new ListAdapter(userList));
                    recyclerView.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);




    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MyUserListAty", "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("MyUserListAty", "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MyUserListAty", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("MyUserListAty", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MyUserListAty", "onDestroy");
    }


}
