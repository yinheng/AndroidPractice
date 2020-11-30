package com.yh.widgetdemo;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingDatePicker extends AppCompatActivity {
    private Button btnDatePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_datepicker);
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnDatePicker.setText("2020年2月1日");
        btnDatePicker.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AtyUsingDatePicker.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnDatePicker.setText(String.format("%d年%d月%d日", year, month + 1, dayOfMonth));
                        Log.d("btnDatePicker text:", btnDatePicker.getText().toString());
                    }
                }, 2020, 1, 1).show();
            }
        });
    }
}
