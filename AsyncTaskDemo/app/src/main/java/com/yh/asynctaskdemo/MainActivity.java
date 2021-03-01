package com.yh.asynctaskdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MyAsyncTaskTest myAsyncTask;
    private TextView textView;
    private ProgressBar progressBar;
    private final int REQUEST_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = this;
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv);
        progressBar = findViewById(R.id.progressBar);
        findViewById(R.id.btnDownload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //使用兼容库就无需判断系统版本
                int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
                    //拥有权限，执行操作
//                    myAsyncTask = new MyAsyncTask(textView, progressBar);
//                    myAsyncTask.execute("https://down.oray.com/sunlogin/mac/");
                    myAsyncTask = new MyAsyncTaskTest(textView, progressBar);
                    myAsyncTask.execute("https://down.oray.com/sunlogin/mac/");
                }else{
                    //没有权限，向用户请求权限
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
                }

            }
        });
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //myAsyncTask.cancel(true);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        if (requestCode == REQUEST_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //用户同意，执行操作
                myAsyncTask = new MyAsyncTaskTest(textView, progressBar);
                myAsyncTask.execute("https://down.oray.com/sunlogin/mac/");
            }else{
                //用户不同意，向用户展示该权限作用
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new AlertDialog.Builder(this)
                            .setMessage("文件读写权限用于下载文件")
                            .setPositiveButton("OK", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(this,
                                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            REQUEST_PERMISSION_CODE))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            }
        }
    }
}