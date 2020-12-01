package com.yh.widgetdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<ListViewCellData> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1);
        arrayAdapter.add(new ListViewCellData("radioGroup", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingRadioGroup.class)));
        arrayAdapter.add(new ListViewCellData("checkBox", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingCheckBox.class)));
        arrayAdapter.add(new ListViewCellData("datePicker", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingDatePicker.class)));
        arrayAdapter.add(new ListViewCellData("timePicker", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingTimePicker.class)));
        arrayAdapter.add(new ListViewCellData("spinner", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingSpinner.class)));
        arrayAdapter.add(new ListViewCellData("autoComplete", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingAutoComplete.class)));
        arrayAdapter.add(new ListViewCellData("seekBar", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingSeekBar.class)));
        arrayAdapter.add(new ListViewCellData("gridView", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingGridView.class)));
        arrayAdapter.add(new ListViewCellData("progressDialog", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingProgressDialog.class)));
        arrayAdapter.add(new ListViewCellData("notification", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingNotification.class)));
        arrayAdapter.add(new ListViewCellData("scrollView", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingScrollView.class)));
        arrayAdapter.add(new ListViewCellData("ratingBar", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingRatingBar.class)));
        arrayAdapter.add(new ListViewCellData("imageSwitcher", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingImageSwitcher.class)));
        arrayAdapter.add(new ListViewCellData("gallery", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingGallery.class)));
        arrayAdapter.add(new ListViewCellData("progressBar", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingProgressBar.class)));
        arrayAdapter.add(new ListViewCellData("editText", MainActivity.this, new Intent().setClass(MainActivity.this, AtyUsingEditText.class)));
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter.getItem(position).startActivity();
            }
        });
    }

    private long lastBackTime = 0;

    @Override
    public void onBackPressed() {

        // 后退键连续两次才会退出应用，防止误触操作
        if ((System.currentTimeMillis() - lastBackTime) > 1000) {
            lastBackTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
        } else {
            lastBackTime = 0;
            super.onBackPressed();
        }
    }
}