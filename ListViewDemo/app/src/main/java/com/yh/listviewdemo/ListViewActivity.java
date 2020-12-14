package com.yh.listviewdemo;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private List<ListViewCell> listViewCellList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        给activity关联布局
        setContentView(R.layout.list_view_layout);
//        列表数据
        listViewCellList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            listViewCellList.add(new ListViewCell("小明" + i, "男", i));
        }
//        列表适配数据
        BaseAdapter baseAdapter = new ListViewAdapter2(listViewCellList);
        listView = findViewById(R.id.listView);
        listView.setAdapter(baseAdapter);
//        给列表添加点击事件
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListViewCell listViewCell = listViewCellList.get(position);
        Toast.makeText(this, String.format("name: %s, sex: %s, age: %d", listViewCell.getName(), listViewCell.getSex(), listViewCell.getAge()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
