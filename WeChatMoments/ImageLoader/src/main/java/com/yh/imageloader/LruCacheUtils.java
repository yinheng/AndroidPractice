package com.yh.imageloader;

import android.graphics.Bitmap;
import android.util.LruCache;

public class LruCacheUtils {
    private LruCache<String, Bitmap> lruCache;
    private static LruCacheUtils lruCacheUtils;

    private LruCacheUtils() {
        initLruCache();
    }

    public static LruCacheUtils getInstance() {
        if(lruCacheUtils == null){
            lruCacheUtils = new LruCacheUtils();
        }
        return lruCacheUtils;
    }

    public void initLruCache() {
        if (lruCache == null) {
            int maxSize = (int) Runtime.getRuntime().totalMemory() / 1024;
            int cacheSize = maxSize / 8;
            lruCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getByteCount() * value.getHeight() / 1024;
                }
            };
        }
    }

    public void reSize(int maxSize) {
        lruCache.resize(maxSize * 1024 * 1024);
    }

    public Bitmap get(String key){
        return lruCache.get(key);
    }

    public Bitmap put(String key, Bitmap bitmap){
        return lruCache.put(key, bitmap);
    }
}
