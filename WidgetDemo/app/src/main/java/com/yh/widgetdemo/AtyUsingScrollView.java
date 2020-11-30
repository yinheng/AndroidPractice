package com.yh.widgetdemo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingScrollView extends AppCompatActivity {
    private TextView tv1, tv2;
    private Button btn1;
    private ImageView img1, img2, img3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_scrollview);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        btn1 = findViewById(R.id.btn1);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        tv1.setText("test");
        tv2.setTextSize(30);
        btn1.setText("test button");
        img1.setImageResource(R.mipmap.img1);
        img2.setImageResource(R.mipmap.img2);
        img3.setImageResource(R.mipmap.img3);
    }
}
