package com.yh.wechatmoments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.yh.asynctask.AsyncTask;
import com.yh.imageloader.ImageLoader;
import com.yh.wechatmoments.db.TweetDatabase;
import com.yh.wechatmoments.model.Tweet;
import com.yh.wechatmoments.model.User;
import com.yh.wechatmoments.retrofit.RetrofitUtils;

import java.io.InputStream;
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
    private static final int RC_CHOOSE_PHOTO = 1;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        appBarLayout = findViewById(R.id.app_bar);
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

        appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, appBarLayout);
                popupMenu.getMenuInflater().inflate(R.menu.menu_change_bkg_img, popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_change:

                                // 相册中选择图片
                                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
                                } else {
                                    //已授权，获取照片
                                    choosePhoto();
                                }
                                break;
                            case R.id.action_cancel:
                                popupMenu.dismiss();
                                break;
                        }
                        return true;
                    }
                });

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

    /**
     * 权限申请结果回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:   //相册选择照片权限申请返回
                choosePhoto();
                break;
        }
    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                try {
                    Uri imageUri = data.getData();//图片的相对路径
                    Log.e("MainActivity", imageUri.toString());

                    //通过流转化成bitmap对象
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap b = BitmapFactory.decodeStream(inputStream);
                    Log.e("MainActivity", " b = " + b);

                    BitmapDrawable db = new BitmapDrawable(getResources(), b);

                    // 将照片显示在 ivImage上
                    appBarLayout.setBackground(db);

                } catch (Throwable e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    class GetUserAsyncTask extends com.yh.asynctask.AsyncTask<String, User> {
        @Override
        protected User doInBackground(String... strings) {
            Global.USER = RetrofitUtils.getUser();

            TweetDatabase.getInstance(MainActivity.this).getUserDao().insertUser(Global.USER);
            Log.e("getUsers", TweetDatabase.getInstance(MainActivity.this).getUserDao().getAllUsers().toString());
            return Global.USER;

        }

        @Override
        protected void completeExecute(User user) {
            super.completeExecute(user);
            if (user != null) {
                Log.e("USER", Global.USER.toString());

                nick.setText(user.getNick());
                imageLoader.loadImage(user.getAvatar(), avatar);
                //imageLoader.loadImage(user.getProfileImage(), profileImgView);
            }
        }
    }

    class GetTweetsAsyncTask extends AsyncTask<Boolean, List<Tweet>> {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected List<Tweet> doInBackground(Boolean... booleans) {
            List<Tweet> tweets = RetrofitUtils.getTweets();
            Global.TWEETS = tweets.stream().filter(new Predicate<Tweet>() {
                @Override
                public boolean test(Tweet tweet) {
                    return !(tweet.getContent() == null && tweet.getSender() == null && tweet.getComments() == null);
                }
            }).collect(Collectors.toList());

            return Global.TWEETS;
        }

        @Override
        protected void completeExecute(List<Tweet> tweetList) {
            super.completeExecute(tweetList);
            Log.e("USER", Global.TWEETS.toString());
            List<Tweet> tweets = new ArrayList<>();
            for (int i = 0; i < MyAdapter.PAGE_SIZE; i++) {
                tweets.add(tweetList.get(i));
            }

            //MyAdapter myAdapter = new MyAdapter(tweets, imageLoader);
            PaginationAdapter paginationAdapter = new PaginationAdapter(tweets, Global.TWEETS.size(), imageLoader);
            recyclerView.setAdapter(paginationAdapter);
            recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(linearLayoutManager);
//            recyclerView.addOnScrollListener(new RecyclerOnScrollListener() {
//                @Override
//                public void loadMoreItems() {
//                    myAdapter.addItems();
//                }
//            });

            if (progressBar.getVisibility() == View.VISIBLE) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }
}