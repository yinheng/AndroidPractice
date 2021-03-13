package com.yh.wechatmoment.view;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.imageloader.ImageLoader;
import com.yh.paginationadapter.PaginationAdapter;
import com.yh.wechatmoment.R;
import com.yh.wechatmoment.model.Comment;
import com.yh.wechatmoment.model.Image;
import com.yh.wechatmoment.model.Tweet;
import com.yh.wechatmoment.viewmodel.TweetViewModel;

import java.util.List;
import java.util.function.Consumer;

public class MyPaginationAdapter extends PaginationAdapter<Tweet> {
    private final ImageLoader imageLoader;
    private final int PAGE_SIZE = 15;

    public MyPaginationAdapter(List<Tweet> list, Integer totalSize, ImageLoader imageLoader) {
        super(list, totalSize);
        this.imageLoader = imageLoader;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Tweet tweet = list.get(position);
        itemViewHolder.content.setText(tweet.getContent());

        imageLoader.loadImage(tweet.getSender().getAvatar(), itemViewHolder.senderAvatar);
        itemViewHolder.senderNick.setText(tweet.getSender().getNick());

        List<Image> images = tweet.getImages();
        RecyclerView imageRecyclerView = itemViewHolder.imageRecyclerView;
        ImagesAdapter imagesAdapter = new ImagesAdapter(images, imageLoader);
        imageRecyclerView.setAdapter(imagesAdapter);
        imageRecyclerView.setLayoutManager(new GridLayoutManager(imageRecyclerView.getContext(), 3));

        List<Comment> comments = tweet.getComments();
        if(comments != null && !comments.isEmpty()){
            RecyclerView commentRecyclerView = itemViewHolder.commentRecyclerView;
            CommentAdapter commentAdapter = new CommentAdapter(comments);
            commentRecyclerView.setAdapter(commentAdapter);
            commentRecyclerView.setLayoutManager(new LinearLayoutManager(commentRecyclerView.getContext()));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Tweet> loadNextPage(Context context, int position) {
        TweetViewModel tweetViewModel = new TweetViewModel(context);
        return tweetViewModel.getNextPage(position, PAGE_SIZE);
//        tweetViewModel.getNextPageItems(new Consumer<List<Tweet>>() {
//            @Override
//            public void accept(List<Tweet> tweets) {
//                list.addAll(tweets);
//            }
//        }, position, PAGE_SIZE);

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView senderAvatar;
        private final TextView senderNick;
        private final TextView content;
        private final RecyclerView imageRecyclerView;
        private final RecyclerView commentRecyclerView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            senderAvatar = itemView.findViewById(R.id.senderAvatar);
            senderNick = itemView.findViewById(R.id.senderNick);
            content = itemView.findViewById(R.id.content);
            imageRecyclerView = itemView.findViewById(R.id.images);
            commentRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);
        }
    }
}
