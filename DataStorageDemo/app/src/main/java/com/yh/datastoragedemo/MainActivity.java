package com.yh.datastoragedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setText("打开应用时弹出对话框");

        SharedPreferences sharedPreferences = getSharedPreferences("isChecked", Context.MODE_PRIVATE);
        checkBox.setChecked(sharedPreferences.getBoolean("isChecked", false));
        if (checkBox.isChecked()) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示")
                    .setMessage("欢迎使用我")
                    .setPositiveButton("确定", null)
                    .show();
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor e = sharedPreferences.edit();
                e.putBoolean("isChecked", isChecked);
                e.apply();

            }
        });

        et = findViewById(R.id.et);
        Button btnSave = findViewById(R.id.btnSave);

        readSavedText();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentText();
            }
        });

        findViewById(R.id.btnUserList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveCurrentText() {
        try {
            FileOutputStream fileOutputStream = openFileOutput("data", Context.MODE_PRIVATE);
            fileOutputStream.write(et.getText().toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
    }

    private void readSavedText() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput("data")));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            et.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}