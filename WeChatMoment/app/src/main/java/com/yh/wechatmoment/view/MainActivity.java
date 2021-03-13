package com.yh.wechatmoment.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yh.imageloader.ImageLoader;
import com.yh.wechatmoment.R;
import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.model.User;
import com.yh.wechatmoment.viewmodel.TweetViewModel;
import com.yh.wechatmoment.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity {
    private RecyclerView tweetsRecyclerView;
    private ImageLoader imageLoader;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        setTitle(null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getResources().getString(R.string.title));
        toolBarLayout.setCollapsedTitleTextColor(Color.BLACK);
        toolBarLayout.setExpandedTitleColor(Color.BLACK);

        imageLoader = new ImageLoader.ImageLoaderBuilder(MainActivity.this, "img").build();

        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        LinearLayout userInfo = findViewById(R.id.userInfo);
        TextView nick = findViewById(R.id.nick);
        ImageView avatar = findViewById(R.id.avatar);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset < 0) {
                    // 收起
                    userInfo.setVisibility(View.GONE);
                    toolBarLayout.setTitleEnabled(true);
                } else {
                    // 展开
                    userInfo.setVisibility(View.VISIBLE);
                    toolBarLayout.setTitleEnabled(false);
                }
            }
        });

        UserViewModel userViewModel = new UserViewModel(this);
        userViewModel.getLocalUserInfo(new Consumer<User>() {
            @Override
            public void accept(User user) {
                if (user != null) {
                    nick.setText(user.getNick());
                    imageLoader.loadImage(user.getAvatar(), avatar);
                }
            }
        });

        userViewModel.getRemoteUserInfo(new Consumer<User>() {
            @Override
            public void accept(User user) {
                if (user != null) {
                    nick.setText(user.getNick());
                    imageLoader.loadImage(user.getAvatar(), avatar);
                }
            }
        });

        tweetsRecyclerView = findViewById(R.id.tweets);
        TweetViewModel tweetViewModel = new TweetViewModel(MainActivity.this);

        tweetViewModel.getRemoteTweetsAndSave(new Consumer<Map<String, Object>>() {
            @Override

            public void accept(Map<String, Object> map) {
                Integer totalCount = (Integer) map.get("totalCount");

                List<Tweet> tweetList = new ArrayList<>();
                if (map.get("tweets") instanceof ArrayList<?>) {
                    for (Object o : (List<?>) Objects.requireNonNull(map.get("tweets"))) {
                        tweetList.add((Tweet) o);
                    }
                }
                tweetsRecyclerView.setAdapter(new MyPaginationAdapter(tweetList, totalCount, imageLoader));
                tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        }, 0, 15);


    }


}