package com.yh.wechatmoments.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageLoader {
    private final LruCacheUtils lruCacheUtils;
    private final DiskCacheUtils diskCacheUtils;
    private final Map<ImageView, LoadImgAsyncTask> taskMap = new HashMap<>();

    private ImageLoader(ImageLoaderBuilder loaderBuilder) {
        this.lruCacheUtils = loaderBuilder.lruCacheUtils;
        this.diskCacheUtils = loaderBuilder.diskCacheUtils;
    }

    public void loadImage(String key, ImageView imageView) {
        Log.e("loadImage", "loadImage begin");
        LoadImgAsyncTask loadImgAsyncTask = taskMap.get(imageView);
        if (loadImgAsyncTask != null) {
            loadImgAsyncTask.cancel(true);
            taskMap.remove(imageView);
        }

        Bitmap bitmap;
        if ((bitmap = lruCacheUtils.get(key)) != null) {
            imageView.setImageBitmap(bitmap);
            Log.e("loadImage", "img exist in lruCache ");
            return;
        }

        loadImgAsyncTask = new LoadImgAsyncTask(imageView);
        loadImgAsyncTask.execute(key);
        taskMap.put(imageView, loadImgAsyncTask);
    }

    public static class ImageLoaderBuilder {
        private final LruCacheUtils lruCacheUtils;
        private final DiskCacheUtils diskCacheUtils;

        public ImageLoaderBuilder(Context context, String subPath) {
            this.lruCacheUtils = LruCacheUtils.getInstance();

            DiskCacheUtils.initDiskCache(context, subPath);
            this.diskCacheUtils = DiskCacheUtils.getInstance();
        }

        public ImageLoaderBuilder setLruCacheSizeForMb(int size) {
            lruCacheUtils.reSize(size);
            return this;
        }

        public ImageLoaderBuilder setDiskCacheSizeForMb(int size) {
            diskCacheUtils.reSize(size);
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }

    class LoadImgAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        private final ImageView imageView;

        public LoadImgAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap;
            String key = strings[0];
            Log.e("loadImage", "get in diskCache begin" + key);
            bitmap = diskCacheUtils.get(String.valueOf(key.hashCode()));
            Log.e("loadImage", "get in diskCache finish" + key);
            if (bitmap != null) {
                lruCacheUtils.put(key, bitmap);
                Log.e("loadImage", "img exist in diskCache ");
                return bitmap;
            }

            bitmap = downloadImg(key);
            if (bitmap != null) {
                lruCacheUtils.put(key, bitmap);
                diskCacheUtils.saveFile(String.valueOf(key.hashCode()), bitmap);
                Log.e("loadImage", "save img to lruCache and diskCache ");
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);

        }

        private Bitmap downloadImg(String url) {
            Log.e("loadImage", "downloadImg");
            Bitmap bitmap = null;
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .get()
                    .url(url)
                    .build();
            Call call = client.newCall(request);
            try {
                Response response = call.execute();
                Log.e("loadImage", "downloadImg: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    InputStream is = Objects.requireNonNull(response.body()).byteStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    Log.e("loadImage", "downloadImg: bitmap" + bitmap.getHeight());
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("loadImage", "downloadImg: e = " + e.getMessage());
            }
            return bitmap;
        }
    }

}
