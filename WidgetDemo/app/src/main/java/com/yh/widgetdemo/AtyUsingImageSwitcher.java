package com.yh.widgetdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageSwitcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingImageSwitcher extends AppCompatActivity {
    private ImageSwitcher imageSwitcher;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_imageswitcher);
        imageSwitcher = findViewById(R.id.imgSwitcher);
        imageSwitcher.setImageResource(R.mipmap.ic_launcher);
        imageSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSwitcher.setImageResource(R.mipmap.img2);
            }
        });
    }
}
