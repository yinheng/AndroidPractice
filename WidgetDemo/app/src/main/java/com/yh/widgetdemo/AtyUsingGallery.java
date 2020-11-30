package com.yh.widgetdemo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Gallery;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingGallery extends AppCompatActivity {
    private Gallery gallery;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_gallery);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (int i = 0; i < 20; i++) {
            arrayAdapter.add("android" + i);
        }
        //gallery类似水平方向的list
        gallery = findViewById(R.id.gallery);
        gallery.setAdapter(arrayAdapter);

    }
}
