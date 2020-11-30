package com.yh.widgetdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingSpinner extends AppCompatActivity {
    private Spinner spinner;
    private String[] strs = new String[]{"hello", "android", "java"};
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_spinner);
        spinner = findViewById(R.id.spinner);
        arrayAdapter = new ArrayAdapter(AtyUsingSpinner.this, android.R.layout.simple_list_item_1);
        arrayAdapter.addAll(strs);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("spinner text", (String) spinner.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
