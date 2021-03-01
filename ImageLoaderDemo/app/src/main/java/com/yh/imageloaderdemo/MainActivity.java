package com.yh.imageloaderdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //requestWritePermission();
    RecyclerView recyclerView = findViewById(R.id.list);
    List<ListCell> listCells = new ArrayList<ListCell>();
    String[] imgUrls = new String[]{
        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2369017631,3935728806&fm=26&gp=0.jpg",
        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3392663359,4194879068&fm=26&gp=0.jpg",
        "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2364244149,3298797080&fm=26&gp=0.jpg",
        "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2321152792,46540900&fm=26&gp=0.jpg",
        "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1422899077,2586179914&fm=26&gp=0.jpg"
    };
    for (int i = 0; i < 100; i++) {
      listCells.add(new ListCell("小明" + i, imgUrls[new Random().nextInt(4)], "hello, 我是小明" + i));
    }
    recyclerView.setAdapter(new MyAdapter(listCells, this));
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

    Log.d("YH", getExternalCacheDir().toString());
    Log.d("YH", getExternalFilesDir("Hello").toString());

    Log.d("YH", getCacheDir().toString());
    Log.d("YH", getFilesDir().toString());

  }

//  @Override
//  public void onRequestPermissionsResult(int requestCode, String permissions[],
//      int[] grantResults) {
//    if (requestCode == 123) {
//      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//        Toast.makeText(this,
//            "You must allow permission write external storage to your mobile device.",
//            Toast.LENGTH_SHORT).show();
//        finish();
//      }
//    }
//  }

  private void requestWritePermission() {
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat
          .requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
    }
  }
}