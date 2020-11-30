package com.yh.widgetdemo;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingAutoComplete extends AppCompatActivity {
    private AutoCompleteTextView actv;
    private MultiAutoCompleteTextView multiactv;
    private ArrayAdapter<String> actvAdapter;
    private String[] strings = new String[]{"hello", "hello android", "hello java", "java", "javascript","python", "php"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_autocomplete);
        actv = findViewById(R.id.tvAutoComplete);
        multiactv = findViewById(R.id.tvMultiAutoComplete);
        actvAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1);
        actvAdapter.addAll(strings);
        actv.setAdapter(actvAdapter);
//        设置多项之间的分隔符，
        multiactv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiactv.setAdapter(actvAdapter);
    }
}
