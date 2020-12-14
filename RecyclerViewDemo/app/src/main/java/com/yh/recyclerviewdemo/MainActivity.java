package com.yh.recyclerviewdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        List<RecyclerViewCell> cellList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            cellList.add(new RecyclerViewCell("R.drawable.ic_baseline_account_circle_24", "小明", "年龄：" + i));
        }
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(cellList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}