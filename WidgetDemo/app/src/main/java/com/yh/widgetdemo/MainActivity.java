package com.yh.widgetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter.getItem(position).startActivity();
            }
        });

    }
}