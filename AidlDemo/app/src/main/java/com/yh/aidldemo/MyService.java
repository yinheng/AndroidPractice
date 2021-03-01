package com.yh.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private final List<User> userList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyService", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("MyService", "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("MyService", "onUnbind");
        return super.onUnbind(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyService", "onBind");
        return new MyBinder();
    }

    public class MyBinder extends UserManagerAidl.Stub {

        @Override
        public List<User> getUsers() throws RemoteException {
            return userList;
        }

        @Override
        public void addUser(User user) throws RemoteException {
            userList.add(user);
        }

        @Override
        public String getString() throws RemoteException {
            return "test";
        }
    }
}
