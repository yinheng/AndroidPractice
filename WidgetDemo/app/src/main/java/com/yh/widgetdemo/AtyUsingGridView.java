package com.yh.widgetdemo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingGridView extends AppCompatActivity {
    private GridView gridView;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_gridview);
        gridView = findViewById(R.id.gridView);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        for(int i = 0; i < 100; i ++){
            arrayAdapter.add("android" + i);
        }
        gridView.setAdapter(arrayAdapter);
    }
}
