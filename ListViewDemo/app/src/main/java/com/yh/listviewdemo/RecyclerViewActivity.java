package com.yh.listviewdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private RecyclerView listView;
    private List<ListViewCell> listViewCellList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_layout);

        listViewCellList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            listViewCellList.add(new ListViewCell("小明" + i, "男", i));
        }

        MyRecyclerAdapter baseAdapter = new MyRecyclerAdapter(listViewCellList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(baseAdapter);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}
