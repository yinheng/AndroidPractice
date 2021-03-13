package com.yh.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

public class MemoryCacheUtils {
    private final LruCache<String, Bitmap> lruCache;
    private static MemoryCacheUtils memoryCacheUtils;
    private MemoryCacheUtils(){
        int maxSize = (int)Runtime.getRuntime().maxMemory() / 8;
        lruCache = new LruCache<String, Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getHeight() * value.getRowBytes();
            }
        };
    }
    public static MemoryCacheUtils getInstance(){
        if(memoryCacheUtils == null){
            memoryCacheUtils = new MemoryCacheUtils();
        }
        return memoryCacheUtils;
    }

    public Bitmap get(String key){
        return lruCache.get(key);
    }

    public void put(String key, Bitmap bitmap){
        lruCache.put(key, bitmap);
    }

    public void delete(String key){
        lruCache.remove(key);
    }

    public void resize(int maxSize){
        lruCache.resize(maxSize);
    }

}
