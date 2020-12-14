package com.yh.listviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//使用BaseAdapter自定义列表
public class ListViewAdapter extends BaseAdapter {
    private List<ListViewCell> listViewCells;

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
        ViewHolder holder;
        if (convertView == null) {
            holder = onCreateViewHolder(parent);
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    protected ViewHolder onCreateViewHolder(ViewGroup parent) {
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_cell, parent, false);
        return new ViewHolder(convertView);
    }

    private void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.mipmap.img1);
        holder.nameTextView.setText(listViewCells.get(position).toString());
        holder.decTextView.setText(listViewCells.get(position).getSex());
    }

    static class ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView decTextView;

        View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            this.imageView = itemView.findViewById(R.id.img);
            this.nameTextView = itemView.findViewById(R.id.name);
            this.decTextView = itemView.findViewById(R.id.dec);
        }
    }
}
