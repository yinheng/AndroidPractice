package com.yh.listviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//使用BaseAdapter自定义列表
public class ListViewAdapter2 extends FastAdapter<ListViewAdapter2.ViewHolder> {
    private List<ListViewCell> listViewCells;

    public ListViewAdapter2(List<ListViewCell> listViewCells) {
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
    protected ViewHolder onCreateViewHolder(ViewGroup parent) {
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_cell, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.mipmap.img1);
        holder.nameTextView.setText(listViewCells.get(position).toString());
        holder.decTextView.setText(listViewCells.get(position).getSex());
    }

    static class ViewHolder extends FastAdapter.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView decTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.img);
            this.nameTextView = itemView.findViewById(R.id.name);
            this.decTextView = itemView.findViewById(R.id.dec);
        }
    }
}
