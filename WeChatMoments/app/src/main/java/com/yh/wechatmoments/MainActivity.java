package com.yh.wechatmoments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yh.wechatmoments.db.TweetDatabase;
import com.yh.wechatmoments.imageloader.ImageLoader;
import com.yh.wechatmoments.model.Tweet;
import com.yh.wechatmoments.model.User;
import com.yh.wechatmoments.retrofit.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private ImageView profileImgView;
    private ImageView avatar;
    private TextView nick;
    private RecyclerView recyclerView;
    private ImageLoader imageLoader;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(null);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getString(R.string.title));
        toolBarLayout.setCollapsedTitleTextColor(Color.BLACK);
        toolBarLayout.setExpandedTitleColor(Color.BLACK);

        imageLoader = new ImageLoader.ImageLoaderBuilder(this, "img")
                .build();

        //profileImgView = findViewById(R.id.background);
        nick = findViewById(R.id.nick);
        avatar = findViewById(R.id.avatar);
        recyclerView = findViewById(R.id.tweetList);
        LinearLayout userInfo = findViewById(R.id.fab);

        progressBar = findViewById(R.id.processBar);
        progressBar.setVisibility(View.VISIBLE);

        new GetUserAsyncTask().execute();
        new GetTweetsAsyncTask().execute();

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.BLUE, Color.RED);
        swipeRefreshLayout.setDistanceToTriggerSync(300);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new GetTweetsAsyncTask().execute();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    // 展开状态
                    swipeRefreshLayout.setEnabled(true);
                    userInfo.setVisibility(View.VISIBLE);

                    toolBarLayout.setTitleEnabled(false);
                } else {

                    // 折叠状态
                    swipeRefreshLayout.setEnabled(false);
                    userInfo.setVisibility(View.GONE);

                    toolBarLayout.setTitleEnabled(true);
                }
            }
        });

    }

    class GetUserAsyncTask extends AsyncTask<String, Integer, User> {
        @Override
        protected User doInBackground(String... strings) {
            Global.USER = RetrofitUtils.getUser();

            TweetDatabase.getInstance(MainActivity.this).getUserDao().insertUser(Global.USER);
            Log.e("getUsers", TweetDatabase.getInstance(MainActivity.this).getUserDao().getAllUsers().toString());
            return Global.USER;

        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            if (user != null) {
                Log.e("USER", Global.USER.toString());

                nick.setText(user.getNick());
                imageLoader.loadImage(user.getAvatar(), avatar);
                //imageLoader.loadImage(user.getProfileImage(), profileImgView);
            }

        }
    }

    class GetTweetsAsyncTask extends AsyncTask<Boolean, Integer, List<Tweet>> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected List<Tweet> doInBackground(Boolean... booleans) {
            List<Tweet> tweets = RetrofitUtils.getTweets();
            Global.TWEETS = tweets.stream().filter(new Predicate<Tweet>() {
                @Override
                public boolean test(Tweet tweet) {
                    return !(tweet.getContent() == null && tweet.getSender() == null && tweet.getComments() == null) ;
                }
            }).collect(Collectors.toList());

            return Global.TWEETS;
        }

        @Override
        protected void onPostExecute(List<Tweet> tweetList) {
            super.onPostExecute(tweetList);
            Log.e("USER", Global.TWEETS.toString());
            List<Tweet> tweets = new ArrayList<>();
            for (int i = 0; i < MyAdapter.PAGE_SIZE; i++) {
                tweets.add(tweetList.get(i));
            }

            MyAdapter myAdapter = new MyAdapter(tweets, imageLoader);
            recyclerView.setAdapter(myAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
                @Override
                public void loadMoreItems() {
                    myAdapter.addItems();
                }
            });

            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}