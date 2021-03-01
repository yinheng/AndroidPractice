package com.yh.imageloaderdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION_CODES;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyImageLoader {

  private MemoryCacheUtil memoryCacheUtil;
  private DiskCacheUtil diskCacheUtil;
  private Map<ImageView, LoadImgFromDiskOrDownloadAsyncTask> asyncTaskMap = new HashMap<ImageView, LoadImgFromDiskOrDownloadAsyncTask>();

  public MyImageLoader(MyImageLoaderBuilder myImageLoaderBuilder) {
    this.diskCacheUtil = myImageLoaderBuilder.diskCacheUtil;
    this.memoryCacheUtil = myImageLoaderBuilder.memoryCacheUtil;
  }

  public void loadImage(String imgUrl, ImageView imageView) {
    imageView.setImageResource(0);
    LoadImgFromDiskOrDownloadAsyncTask existTask = asyncTaskMap.get(imageView);
    if(existTask != null){
      existTask.cancel(true);
      asyncTaskMap.remove(imageView);
    }

    Bitmap bitmap =null;
    if ((bitmap = memoryCacheUtil.getBitmap(imgUrl)) != null) {
      imageView.setImageBitmap(bitmap);
      return;
    }

    LoadImgFromDiskOrDownloadAsyncTask loadImgTask = new LoadImgFromDiskOrDownloadAsyncTask(imageView);
    loadImgTask.execute(imgUrl);
    asyncTaskMap.put(imageView, loadImgTask);
  }

  public class LoadImgFromDiskOrDownloadAsyncTask extends AsyncTask<String, Integer, Bitmap> {

    private final ImageView imageView;

    public LoadImgFromDiskOrDownloadAsyncTask(ImageView imageView) {
      this.imageView = imageView;
    }

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected Bitmap doInBackground(String... strings) {
      String url = strings[0];
      Bitmap bitmap;

      bitmap = diskCacheUtil.get(Uri.encode(url));
      if (bitmap != null) {
        memoryCacheUtil.put(url, bitmap);
        return bitmap;
      }

      return downlaodImg(url);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      super.onPostExecute(bitmap);
      imageView.setImageBitmap(bitmap);
    }
  }

  @RequiresApi(api = VERSION_CODES.N)
  private Bitmap downlaodImg(String url){
    Bitmap bitmap = null;
    OkHttpClient client = new OkHttpClient.Builder()
        .callTimeout(60, TimeUnit.SECONDS)
        .build();
    Request request = new Request.Builder()
        .url(url)
        .get()
        .build();
    try {
      Response response = client.newCall(request).execute();
      if (response != null && response.body() != null) {
        InputStream is = response.body().byteStream();
        bitmap = BitmapFactory.decodeStream(is);

        memoryCacheUtil.put(url, bitmap);
        diskCacheUtil.putImgToDisk(Uri.encode(url), bitmap);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  public static class MyImageLoaderBuilder {

    private DiskCacheUtil diskCacheUtil;
    private final MemoryCacheUtil memoryCacheUtil = MemoryCacheUtil.getInstånce();

    public MyImageLoaderBuilder setDiskCache(Context context, String path) {
      DiskCacheUtil.init(context, path);
      this.diskCacheUtil = DiskCacheUtil.getInstance();
      return this;
    }

    public MyImageLoaderBuilder setDiskCacheSize_MB(Integer size) {
      diskCacheUtil.setCacheSizeForMB(size);
      return this;
    }

    public MyImageLoaderBuilder setMemoryCacheSize_B(Integer size) {
      memoryCacheUtil.setSizeForByte(size);
      return this;
    }

    public MyImageLoader build() {
      return new MyImageLoader(this);
    }
  }

}
