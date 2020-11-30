package com.yh.widgetdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingRadioGroup extends AppCompatActivity {
    private TextView textView;
    private RadioButton rightBtn;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_radiogroup);
        textView = findViewById(R.id.textView);
        textView.setText("老师是帅哥吗");
        rightBtn = findViewById(R.id.btnRight);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AtyUsingRadioGroup.this)
                        .setTitle("判断")
                        .setMessage(isRight())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }
    private String isRight(){
        return rightBtn.isChecked()?"正确":"错误";
    }
}
