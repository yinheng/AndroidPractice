package com.yh.wechatmoments;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.imageloader.ImageLoader;
import com.yh.wechatmoments.model.Comment;
import com.yh.wechatmoments.model.Image;
import com.yh.wechatmoments.model.Sender;
import com.yh.wechatmoments.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends com.yh.paginationadapter.PaginationAdapter<RecyclerView.ViewHolder, Tweet> {

    private final ImageLoader imageLoader;

    public PaginationAdapter(List<Tweet> list, int totalCount, ImageLoader imageLoader) {
        super(list, totalCount);
        this.imageLoader = imageLoader;
    }


    @Override
    public MyViewHolder createItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void bindItemViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Tweet tweet = list.get(position);
        Log.e("MyAdapter", tweet.toString());

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Sender sender = tweet.getSender();
        if (sender != null) {
            //imageLoader.loadImage(tweet.getSender().getAvatar(), ((MyViewHolder) holder).avatar);
            myViewHolder.nick.setText(tweet.getSender().getNick());
        }


        if (tweet.getContent() == null) {
            myViewHolder.content.setVisibility(View.GONE);
        } else {
            myViewHolder.content.setVisibility(View.VISIBLE);
            myViewHolder.content.setText(tweet.getContent());
        }

        // 九宫格图片
        List<Image> imageList = tweet.getImgList();

        RecyclerView rv = myViewHolder.rv;
        rv.setAdapter(new ImgsRecyclerViewAdapter(imageList, imageLoader));
        rv.setLayoutManager(new GridLayoutManager(rv.getContext(), 3));

        // comments
        CardView cardView = myViewHolder.commentsCardView;
        LinearLayout commentsLinearLayout = myViewHolder.comments;
        commentsLinearLayout.removeAllViews();
        List<Comment> comments = tweet.getComments();
        if (comments != null && !comments.isEmpty()) {
            cardView.setVisibility(View.VISIBLE);
            addComments(commentsLinearLayout, comments);
            return;
        }
        cardView.setVisibility(View.GONE);

    }

    @Override
    public List<Tweet> findNextPage(int currentItemCount) {

        List<Tweet> tweets = new ArrayList<>();
        for (int i = currentItemCount; i < currentItemCount + PAGE_SIZE; i++) {
            if (i < Global.TWEETS.size()) {
                Tweet tweet = Global.TWEETS.get(i);
                tweets.add(tweet);
            }
        }
        return tweets;
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView nick;
        private TextView content;
        private LinearLayout comments;
        private CardView commentsCardView;
        private RecyclerView rv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            nick = itemView.findViewById(R.id.nick);
            content = itemView.findViewById(R.id.content);
            comments = itemView.findViewById(R.id.comments);
            commentsCardView = itemView.findViewById(R.id.commentsCardView);
            rv = itemView.findViewById(R.id.imgRecyclerView);
        }
    }

    private void addComments(LinearLayout parent, List<Comment> comments) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (Comment comment : comments) {
            LinearLayout linearLayout = new LinearLayout(parent.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            parent.addView(linearLayout, params);

            params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            TextView nick = new TextView(parent.getContext());
            nick.setTextColor(Color.BLACK);
            nick.setText(String.format("%s : ", comment.getSender().getNick()));

            TextView content = new TextView(parent.getContext());
            content.setTextColor(Color.GRAY);
            content.setText(comment.getContent());

            linearLayout.addView(nick);
            linearLayout.addView(content, params);
        }
    }
}
