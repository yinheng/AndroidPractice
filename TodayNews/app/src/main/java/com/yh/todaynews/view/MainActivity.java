package com.yh.todaynews.view;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.yh.imageloader.ImageLoader;
import com.yh.todaynews.R;

public class MainActivity extends AppCompatActivity {
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageLoader = new ImageLoader.ImageLoaderBuilder(this, "img").build();
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        SearchView searchView = findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MainActivity.this, "close", Toast.LENGTH_SHORT);
                searchView.setSubmitButtonEnabled(false);
                searchView.setFocusable(false);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText == null || newText.equals("")){
                    searchView.setFocusable(false);
                    searchView.setSubmitButtonEnabled(false);
                }
                else {
                    searchView.setSubmitButtonEnabled(true);
                }
                return false;
            }
        });
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}