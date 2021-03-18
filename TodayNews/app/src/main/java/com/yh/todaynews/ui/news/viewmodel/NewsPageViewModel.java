package com.yh.todaynews.ui.news.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.yh.todaynews.data.repositories.Repositories;
import com.yh.todaynews.ui.news.model.News;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NewsPageViewModel {
    private final Repositories repositories;

    public NewsPageViewModel(Context context) {
        repositories = new Repositories(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getLocalNewsByPage(Consumer<Map<String, Object>> consumer, int start, int pageSize) {
        new AsyncTask<Integer, Integer, Map<String, Object>>() {
            @Override
            protected Map<String, Object> doInBackground(Integer... integers) {
                int start = integers[0];
                int pageSize = integers[1];
                Map<String, Object> result = new HashMap<>();
                int total = repositories.getLocalTotalCount();
                List<News> newsList = repositories.getLocalNewsByPage(start, pageSize);
                result.put("total", total);
                result.put("newsList", newsList);
                return result;
            }

            @Override
            protected void onPostExecute(Map<String, Object> result) {
                super.onPostExecute(result);
                consumer.accept(result);
            }
        }.execute(start, pageSize);
    }

    public List<News> getNextPage(int start, int pageSize) {
        return repositories.getLocalNewsByPage(start, pageSize);
    }

    public void getLoadTotalCount(Consumer<Integer> consumer) {
        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... integers) {
                return repositories.getLocalTotalCount();
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void onPostExecute(Integer integer) {
                consumer.accept(integer);
            }
        }.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getRemoteNews(Consumer<List<News>> consumer) {
        List<News> news = new ArrayList<>();
        new AsyncTask<String, Integer, List<News>>() {
            @Override
            protected List<News> doInBackground(String... strings) {
                return repositories.getRemoteNews();
            }

            @Override
            protected void onPostExecute(List<News> news) {
                super.onPostExecute(news);
                consumer.accept(news);
            }
        }.execute();
        consumer.accept(news);
    }
}