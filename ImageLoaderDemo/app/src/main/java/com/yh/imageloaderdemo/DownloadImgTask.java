package com.yh.imageloaderdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadImgTask extends AsyncTask<String, Integer, Bitmap> {

  private ImageView imgView;

  public DownloadImgTask(ImageView imgView) {
    this.imgView = imgView;
  }

  @Override
  protected Bitmap doInBackground(String... strings) {
    return getImgBitMap(strings[0]);
  }

  @Override
  protected void onPostExecute(Bitmap bitmap) {
    super.onPostExecute(bitmap);
    imgView.setImageBitmap(bitmap);

  }

  /**
   * 从服务器取图片
   *
   * @param url
   * @return
   */
  public static Bitmap getHttpBitmap(String url) {
    URL myFileUrl = null;
    Bitmap bitmap = null;
    try {
      myFileUrl = new URL(url);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    try {
      HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
      conn.setConnectTimeout(0);
      conn.setDoInput(true);
      conn.connect();
      InputStream is = conn.getInputStream();
      bitmap = BitmapFactory.decodeStream(is);
      is.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  private Bitmap getImgBitMap(String url) {
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
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bitmap;
  }
}
