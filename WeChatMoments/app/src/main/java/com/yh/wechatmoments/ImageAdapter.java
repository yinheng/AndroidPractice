package com.yh.wechatmoments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yh.wechatmoments.imageloader.ImageLoader;
import com.yh.wechatmoments.model.Image;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    private List<Image> imageList;
    private ImageLoader imageLoader;

    public ImageAdapter(List<Image> imageList, ImageLoader imageLoader) {
        this.imageList = imageList;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image = imageList.get(position);
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(parent.getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(250, 300));

        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setPadding(24, 24, 24, 24);
        imageLoader.loadImage(image.getUrl(), imageView);
        return imageView;
    }
}
