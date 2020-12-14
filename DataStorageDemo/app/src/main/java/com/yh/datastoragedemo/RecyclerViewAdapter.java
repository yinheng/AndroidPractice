package com.yh.datastoragedemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final List<User> userList;
    private final UserDB userDB;

    public RecyclerViewAdapter(List<User> userList, UserDB userDB) {
        this.userList = userList;
        this.userDB = userDB;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlist_cell, parent, false);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.name.setText(user.getName());
        holder.sex.setText(user.getSex());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("提示")
                        .setMessage("确定删除吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SQLiteDatabase writeDB = userDB.getWritableDatabase();
                                writeDB.delete("user", "id=?", new String[]{user.getId() + ""});
                                writeDB.close();
                                userList.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(v.getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView name, sex;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        sex = itemView.findViewById(R.id.sex);
    }
}
