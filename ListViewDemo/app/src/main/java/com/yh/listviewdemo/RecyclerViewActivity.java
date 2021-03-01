package com.yh.listviewdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);


        MyRecyclerAdapter baseAdapter = new MyRecyclerAdapter(new ArrayList<>());
        listView = findViewById(R.id.listView);
        listView.setAdapter(baseAdapter);
        listView.setLayoutManager(new GridLayoutManager(this, 2));

        ProgressBar progressBar = findViewById(R.id.progress_horizontal);

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ListViewCell> listViewCellList = new ArrayList<>();
                for (int i = 0; i < 100; i++) {
                    listViewCellList.add(new ListViewCell("小明" + i, "男", i));
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        baseAdapter.update(listViewCellList);
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
    }
}
