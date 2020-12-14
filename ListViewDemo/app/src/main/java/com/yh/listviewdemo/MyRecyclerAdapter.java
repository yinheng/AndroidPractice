package com.yh.listviewdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<ListViewCell> listViewCells;

    public MyRecyclerAdapter(List<ListViewCell> listViewCells) {
        this.listViewCells = listViewCells;
    }

    @Override
    public int getItemCount() {
        return listViewCells.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_cell_recycler, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(R.mipmap.img1);
        holder.nameTextView.setText(listViewCells.get(position).toString());
        holder.decTextView.setText(listViewCells.get(position).getSex());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListViewCell listViewCell = listViewCells.get(position);
                Toast.makeText(v.getContext(),
                        String.format("name: %s, sex: %s, age: %d", listViewCell.getName(), listViewCell.getSex(), listViewCell.getAge()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
