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
        LinearLayout linearLayout = null;
        if (convertView != null) {
            linearLayout = (LinearLayout) convertView;
        } else {
            linearLayout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_cell, null);
        }

        ImageView imageView = linearLayout.findViewById(R.id.img);
        TextView nameTextView = linearLayout.findViewById(R.id.name);
        TextView decTextView = linearLayout.findViewById(R.id.dec);

        imageView.setImageResource(R.mipmap.img1);
        nameTextView.setText(listViewCells.get(position).toString());
        decTextView.setText(listViewCells.get(position).getSex());
        return linearLayout;

    }
}
