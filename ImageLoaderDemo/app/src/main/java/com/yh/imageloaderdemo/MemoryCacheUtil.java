package com.yh.imageloaderdemo;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoryCacheUtil {

  private static LruCache<String, Bitmap> lruCache;
  private static MemoryCacheUtil memoryCacheUtil;

  private MemoryCacheUtil() {
    int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;

    lruCache = new LruCache<String, Bitmap>(maxMemory) {
      @Override
      protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() / 1024;
      }
    };
  }

  public static MemoryCacheUtil getInstånce(){
    if (memoryCacheUtil == null) {
      memoryCacheUtil = new MemoryCacheUtil();
    }
    return memoryCacheUtil;
  }

  public void setSizeForByte(Integer maxSize) {
    if (maxSize != null) {
      lruCache.resize(maxSize);
    }
  }

  public void put(String key, Bitmap bitmap) {
    if (getBitmap(key) == null) {
      lruCache.put(key, bitmap);
    }
  }

  public  Bitmap getBitmap(String key) {
    return lruCache.get(key);
  }

  public  void clearAll() {
    lruCache.evictAll();
  }

  public  Bitmap remove(String key) {
    return lruCache.remove(key);
  }

}
