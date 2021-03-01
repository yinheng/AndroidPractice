package com.yh.wechatmoments;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.wechatmoments.imageloader.ImageLoader;
import com.yh.wechatmoments.model.Comment;
import com.yh.wechatmoments.model.Image;
import com.yh.wechatmoments.model.Tweet;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Tweet> tweetList;
    private final ImageLoader imageLoader;
    public final static int PAGE_SIZE = 5;
    private final int ITEM_TYPE = 1;
    private final int FOOTER_TYPE = 2;

    // 加载中
    public final int LOADING = 1;

    // 加载完成
    public final int LOADING_COMPLETE = 2;

    // 加载到底
    public final int LOADING_END = 3;

    private int loadState = LOADING;


    public MyAdapter(List<Tweet> tweetList, ImageLoader imageLoader) {
        this.tweetList = tweetList;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            Log.e("getItemViewType", "FOOTER_TYPE position = " + position);
            return FOOTER_TYPE;
        }

        Log.e("getItemViewType", "ITEM_TYPE position = " + position);
        return ITEM_TYPE;
    }

    public void setLoadState(int loadState) {
        Log.e("MyAdapter", "setLoadState");
        boolean stateChanged = loadState != this.loadState;
        this.loadState = loadState;
        if (stateChanged) {
            // notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == ITEM_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_layout, parent, false);
            viewHolder = new MyViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_layout, parent, false);
            viewHolder = new LoadMoreViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            switch (loadState) {
                case LOADING:
                    Log.e("LOADING", "position = " + position);
                    loadMoreViewHolder.progressBar.setVisibility(View.VISIBLE);
                    loadMoreViewHolder.loadMoreText.setVisibility(View.VISIBLE);
                    loadMoreViewHolder.loadEnd.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:
                    loadMoreViewHolder.progressBar.setVisibility(View.GONE);
                    loadMoreViewHolder.loadMoreText.setVisibility(View.GONE);
                    loadMoreViewHolder.loadEnd.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    loadMoreViewHolder.progressBar.setVisibility(View.GONE);
                    loadMoreViewHolder.loadMoreText.setVisibility(View.GONE);
                    loadMoreViewHolder.loadEnd.setVisibility(View.VISIBLE);
                    break;
            }

            return;
        }

        Tweet tweet = tweetList.get(position);

        //imageLoader.loadImage(tweet.getSender().getAvatar(), holder.avatar);
        //holder.nick.setText(tweet.getSender().getNick());
        ((MyViewHolder) holder).content.setText(tweet.getContent());

        GridView gridView = ((MyViewHolder) holder).gridView;
        List<Image> imageList = tweet.getImgList();
//
//        gridView.setAdapter(new ImageAdapter(imageList, imageLoader));
//        int imageCount = imageList == null ? 0 : imageList.size();
//        int rowCount = 0;
//        if (imageCount <= 3) {
//            rowCount = 1;
//        } else if (imageCount <= 6) {
//            rowCount = 2;
//        } else {
//            rowCount = 3;
//        }
//        int gridViewHeight = rowCount * 300;
//        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
//        layoutParams.height = gridViewHeight;
//        gridView.setLayoutParams(layoutParams);

        RecyclerView rv = ((MyViewHolder) holder).rv;
        rv.setAdapter(new ImgsRecyclerViewAdapter(imageList, imageLoader));
        rv.setLayoutManager(new GridLayoutManager(rv.getContext(), 3));


//        LinearLayout imgLinearLayout = holder.imgList;
//        imgLinearLayout.removeAllViews();
//        if (imageList != null && !imageList.isEmpty()) {
//            addTweetImgList(imgLinearLayout, imageList);
//        }

        LinearLayout commentsLinearLayout = ((MyViewHolder) holder).comments;
        commentsLinearLayout.removeAllViews();
        List<Comment> comments = tweet.getComments();
        if (comments != null && !comments.isEmpty()) {
            addComments(commentsLinearLayout, comments);
        }
    }

    @Override
    public int getItemCount() {
        return tweetList.size() + 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView nick;
        private TextView content;
        private LinearLayout imgList;
        private GridView gridView;
        private LinearLayout comments;
        private RecyclerView rv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            nick = itemView.findViewById(R.id.nick);
            content = itemView.findViewById(R.id.content);
            imgList = itemView.findViewById(R.id.imgLIst);
            comments = itemView.findViewById(R.id.comments);
            gridView = itemView.findViewById(R.id.imgs);
            rv = itemView.findViewById(R.id.imgRecyclerView);
        }
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;
        private TextView loadMoreText;
        private LinearLayout loadEnd;

        public LoadMoreViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.process);
            loadMoreText = itemView.findViewById(R.id.loadMoreText);
            loadEnd = itemView.findViewById(R.id.loadEnd);
        }
    }

    private void addTweetImgList(LinearLayout linearLayout, List<Image> imageList) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(2, 0, 2, 0);

        if (imageList.size() <= 3) {
            addImageViewsOnHorizontal(linearLayout, imageList);
        } else if (imageList.size() <= 6) {
            addImageViewsOnHorizontal(linearLayout, imageList.subList(0, 3));
            addImageViewsOnHorizontal(linearLayout, imageList.subList(3, imageList.size()));
        } else if (imageList.size() <= 9) {
            addImageViewsOnHorizontal(linearLayout, imageList.subList(0, 3));
            addImageViewsOnHorizontal(linearLayout, imageList.subList(3, 6));
            addImageViewsOnHorizontal(linearLayout, imageList.subList(6, imageList.size()));
        }
    }

    private void addImageViewsOnHorizontal(LinearLayout parent, List<Image> images) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parent.addView(linearLayout);
        params.width = 250;
        params.height = 300;
        params.setMargins(2, 1, 2, 1);

        for (Image image : images) {
            ImageView imageView = new ImageView(parent.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageLoader.loadImage(image.getUrl(), imageView);
            linearLayout.addView(imageView, params);
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
            nick.setText(comment.getSender().getNick() + ": ");

            TextView content = new TextView(parent.getContext());
            content.setTextColor(Color.GRAY);
            content.setText(comment.getContent());

            linearLayout.addView(nick);
            linearLayout.addView(content, params);
        }
    }

    public void addItems() {
        int currentItem = getItemCount() - 1;
        int totalItem = Global.TWEETS.size();
        Log.e("onScrollState addItems", "currentItem = " + currentItem);
        if (currentItem < totalItem) {
            setLoadState(LOADING);
            notifyItemChanged(currentItem);
            Log.e("onScrollState addItems", "currentItem < totalItem");
            for (int i = currentItem; i < currentItem + PAGE_SIZE; i++) {
                if (i < Global.TWEETS.size()) {
                    Tweet tweet = Global.TWEETS.get(i);
                    tweetList.add(tweet);
                }
            }
            setLoadState(LOADING_COMPLETE);
            notifyItemRangeChanged(currentItem, tweetList.size());
        } else {
            Log.e("onScrollState addItems", "LOADING_END");
            setLoadState(LOADING_END);
            notifyItemRangeChanged(currentItem, tweetList.size());
        }

    }
}
