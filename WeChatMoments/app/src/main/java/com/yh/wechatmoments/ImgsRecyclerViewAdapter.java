package com.yh.wechatmoments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yh.wechatmoments.imageloader.ImageLoader;
import com.yh.wechatmoments.model.Image;

import java.util.List;

public class ImgsRecyclerViewAdapter extends RecyclerView.Adapter<ImgsRecyclerViewAdapter.ViewHolder> {
    private List<Image> imageList;
    private ImageLoader imageLoader;

    public ImgsRecyclerViewAdapter(List<Image> imageList, ImageLoader imageLoader) {
        this.imageList = imageList;
        this.imageLoader = imageLoader;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView imageView = holder.imageView;
        imageLoader.loadImage(imageList.get(position).getUrl(), imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image);
        }
    }
}
