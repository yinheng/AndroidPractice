package com.yh.widgetdemo;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingTimePicker extends AppCompatActivity {
    private Button btnTimePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_timepicker);
        btnTimePicker = findViewById(R.id.btnTimePicker);
        btnTimePicker.setText("00:00");
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(AtyUsingTimePicker.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        btnTimePicker.setText(String.format("%s:%s", timeFormat(hourOfDay), timeFormat(minute)));
                    }
                }, 10, 22, true).show();
            }
        });
    }

    private String timeFormat(int time) {
        return time < 10 ? "0" + time : "" + time;
    }
}
