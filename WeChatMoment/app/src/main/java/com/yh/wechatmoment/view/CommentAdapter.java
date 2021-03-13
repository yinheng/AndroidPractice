package com.yh.wechatmoment.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.wechatmoment.R;
import com.yh.wechatmoment.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;

    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_layout, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.commentSender.setText(String.format("%s : ", comment.getSender().getNick()));
        holder.commentContent.setText(comment.getContent());

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final TextView commentSender;
        private final TextView commentContent;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentSender = itemView.findViewById(R.id.commentSender);
            commentContent = itemView.findViewById(R.id.commentContent);
        }
    }
}
