package com.yh.widgetdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingRatingBar extends AppCompatActivity {
    private RatingBar ratingBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_ratingbar);
        ratingBar = findViewById(R.id.ratingBar);
//        设置星星数量
        ratingBar.setNumStars(5);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(AtyUsingRatingBar.this, "评分为：" + rating, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
