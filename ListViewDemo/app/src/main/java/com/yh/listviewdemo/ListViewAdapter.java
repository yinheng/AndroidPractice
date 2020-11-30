package com.yh.listviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
//使用BaseAdapter自定义列表
public class ListViewAdapter extends BaseAdapter {
    private List<ListViewCell> listViewCells;
    private ImageView imageView;
    private TextView t;


    public ListViewAdapter(List<ListViewCell> listViewCells) {
        this.listViewCells = listViewCells;
    }

    @Override
    public int getCount() {
        return listViewCells.size();
    }

    @Override
    public ListViewCell getItem(int position) {
        return listViewCells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        TextView textView = null;
//        if(convertView ==null){
//            textView = new TextView(parent.getContext());
//        }
//        else {
//            textView = (TextView) convertView;
//        }
//        textView.setText(listViewCells.get(position).toString());
//        return textView;
//        convertView为回收的对象,如果不为空，可重复利用，为空则新创建对象
        LinearLayout linearLayout = null;
        if (convertView != null) {
            linearLayout = (LinearLayout) convertView;
        } else {
//            利用layout解释器来找到layout对象
            linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_cell, null);
        }
//        利用id找到布局下的控件
        ImageView imageView = linearLayout.findViewById(R.id.img);
        TextView nameTextView = linearLayout.findViewById(R.id.name);
        TextView decTextView = linearLayout.findViewById(R.id.dec);
//给控件赋值
        imageView.setImageResource(R.mipmap.img1);
        nameTextView.setText(listViewCells.get(position).toString());
        decTextView.setText(listViewCells.get(position).getSex());
        return linearLayout;

    }
}
