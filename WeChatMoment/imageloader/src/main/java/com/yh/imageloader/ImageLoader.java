package com.yh.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.yh.asynctask.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageLoader {
    protected final MemoryCacheUtils memoryCacheUtils;
    protected final DiskCacheUtils diskCacheUtils;

    private ImageLoader(ImageLoaderBuilder imageLoaderBuilder) {

        memoryCacheUtils = imageLoaderBuilder.memoryCacheUtils;
        diskCacheUtils = imageLoaderBuilder.diskCacheUtils;
    }

    public void loadImage(String url, ImageView imageView) {
        Bitmap bitmap = memoryCacheUtils.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        new LoadImageTask(imageView).execute(url);
    }

    public static class ImageLoaderBuilder {
        private final MemoryCacheUtils memoryCacheUtils;
        private final DiskCacheUtils diskCacheUtils;

        public ImageLoaderBuilder(Context context, String subPath) {

            this.memoryCacheUtils = MemoryCacheUtils.getInstance();
            DiskCacheUtils.initDiskCache(context, subPath);
            this.diskCacheUtils = DiskCacheUtils.getInstance();
        }

        public ImageLoaderBuilder setMemoryCacheSize(int maxSize) {
            memoryCacheUtils.resize(maxSize);
            return this;
        }

        public ImageLoaderBuilder setDiskCacheSize(int maxSize) {
            diskCacheUtils.reSizeByMB(maxSize);
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }

    class LoadImageTask extends AsyncTask<String, Bitmap> {
        private final ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Bitmap doInBackground(String... strings) {
            String key = strings[0];
            Bitmap bitmap = diskCacheUtils.get(String.valueOf(key.hashCode()));
            if (bitmap != null) {
                memoryCacheUtils.put(key, bitmap);
                return bitmap;
            }

            bitmap = downloadImage(key);
            if(bitmap != null){
                memoryCacheUtils.put(key, bitmap);
                diskCacheUtils.saveFile(String.valueOf(key.hashCode()), bitmap);
            }
            return bitmap;
        }

        private Bitmap downloadImage(String key) {
            Bitmap bitmap = null;
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url(key)
                    .get()
                    .build();
            Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    InputStream is = Objects.requireNonNull(response.body()).byteStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("ImageLoader", e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void doComplete(Bitmap bitmap) {
            super.doComplete(bitmap);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
