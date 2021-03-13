package com.yh.wechatmoment.viewmodel;

import android.content.Context;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.yh.asynctask.AsyncTask;
import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.repositories.Repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TweetViewModel {
    private final Repositories repositories;

    public TweetViewModel(Context context) {
        repositories = new Repositories(context);
    }

    public void getRemoteTweetsAndSave(Consumer<Map<String, Object>> consumer, int start, int pageSize) {
        new AsyncTask<Integer, Map<String, Object>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected Map<String, Object> doInBackground(Integer... integers) {
                int start = integers[0];
                int pageSize = integers[1];
                Map<String, Object> resultMap = new HashMap<>();
                List<Tweet> tweetList = repositories.getRemoteTweetList();
                repositories.saveRemoteTweets(tweetList);
                resultMap.put("totalCount", repositories.getTweetsTotalCount());
                resultMap.put("tweets", repositories.getLocalTweetListByPage(start, pageSize));

                return resultMap;
            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void doComplete(Map<String, Object> map) {
                super.doComplete(map);
                consumer.accept(map);
            }
        }.execute(start, pageSize);

    }

    public void getNextPageItems(Consumer<List<Tweet>> consumer, int start, int pageSize){
        new AsyncTask<Integer, List<Tweet>>(){

            @Override
            protected List<Tweet> doInBackground(Integer... integers) {
                int start = integers[0];
                int pageSize = integers[1];
                return repositories.getLocalTweetListByPage(start, pageSize);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void doComplete(List<Tweet> tweets) {
                super.doComplete(tweets);
                consumer.accept(tweets);
            }
        }.execute(start, pageSize);
    }

    public List<Tweet> getNextPage(int start, int pageSize){
        return repositories.getLocalTweetListByPage(start, pageSize);
    }
}
