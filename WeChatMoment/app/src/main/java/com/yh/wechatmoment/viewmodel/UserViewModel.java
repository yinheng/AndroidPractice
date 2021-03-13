package com.yh.wechatmoment.viewmodel;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.yh.asynctask.AsyncTask;
import com.yh.wechatmoment.model.User;
import com.yh.wechatmoment.repositories.Repositories;

import java.util.function.Consumer;

public class UserViewModel {
    private Repositories repositories;

    public UserViewModel(Context context) {
        this.repositories = new Repositories(context);
    }

    public void getLocalUserInfo(Consumer<User> consumer) {
        new AsyncTask<String, User>() {

            @Override
            protected User doInBackground(String... strings) {
                return repositories.getLocalUser();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void doComplete(User user) {
                super.doComplete(user);
                consumer.accept(user);
            }
        }.execute();
    }

    public void getRemoteUserInfo(Consumer<User> consumer) {
        new AsyncTask<String, User>() {

            @Override
            protected User doInBackground(String... strings) {
                return repositories.getRemoteUserAndSave();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void doComplete(User user) {
                super.doComplete(user);
                consumer.accept(user);
            }
        }.execute();
    }
}
