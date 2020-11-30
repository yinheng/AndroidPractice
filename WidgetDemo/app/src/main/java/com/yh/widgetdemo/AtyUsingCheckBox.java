package com.yh.widgetdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingCheckBox extends AppCompatActivity {
    private Button submitBtn;
    private CheckBox apple, orange, rice, banana;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_checkbox);
        submitBtn = findViewById(R.id.btnSubmit);
        apple = findViewById(R.id.apple);
        orange = findViewById(R.id.orange);
        rice = findViewById(R.id.rice);
        banana = findViewById(R.id.banana);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AtyUsingCheckBox.this)
                        .setTitle("结果")
                        .setMessage(getContent())
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }

    private String getContent() {
        StringBuffer contentBuffer = new StringBuffer("中午吃的有：\n");
        if (apple.isChecked()) {
            contentBuffer.append("苹果\n");
        }
        if (rice.isChecked()) {
            contentBuffer.append("米饭\n");
        }
        if (orange.isChecked()) {
            contentBuffer.append("橘子\n");
        }
        if (banana.isChecked()) {
            contentBuffer.append("香蕉\n");
        }
        return contentBuffer.toString();
    }
}
