package com.yh.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiskCacheUtils {
    private static DiskCacheUtils diskCacheUtils;
    private static String path;
    private long maxSize;

    private DiskCacheUtils() {
    }

    public static DiskCacheUtils getInstance() {

        return diskCacheUtils;
    }

    public static void initDiskCache(Context context, String subPath) {
        path = context.getExternalCacheDir().getAbsolutePath() + "/" + subPath;
        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }
        if (diskCacheUtils == null) {
            diskCacheUtils = new DiskCacheUtils();
        }
    }

    public void reSize(int maxSize) {
        this.maxSize = maxSize * 1024 * 1024;
    }

    public Bitmap get(String key) {
        Bitmap bitmap = null;
        String filePath = path + "/" + key;
        File file = new File(filePath);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(filePath);
        }
        return bitmap;
    }

    private void put(String key, Bitmap bitmap) {
        if (bitmap != null) {
            File file = new File(path + "/" + key);

            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("DiskCacheUtils", "put:" + e.getMessage());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveFile(String key, Bitmap bitmap) {
        if (maxSize == 0) {
            put(key, bitmap);
            return;
        }

        long freeSize = getFreeSize();
        long bitmapSize = bitmap.getByteCount();
        if (bitmapSize < freeSize) {
            put(key, bitmap);
            return;
        }

        deleteLastFile();
        saveFile(key, bitmap);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteLastFile() {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null && files.length != 0) {
            List<File> fileList = Arrays.stream(files).sorted(Comparator.comparing(new Function<File, Long>() {
                @Override
                public Long apply(File file) {
                    return file.lastModified();
                }
            })).collect(Collectors.toList());
            fileList.get(0).deleteOnExit();
        }
    }

    private long getFreeSize() {
        long freeSize = 0;
        if (maxSize != 0) {
            long usedSize = getUsedSize(path);
            Log.e("getUsedSize", "" + usedSize);
            if (maxSize > usedSize) {
                freeSize = maxSize - usedSize;
            }
        }
        return freeSize;
    }

    private long getUsedSize(String path) {
        long usedSize = 0;
        File file = new File(path);
        Log.e("usedSize", "" + file.getTotalSpace());
        if (file.isDirectory()) {
            for (File file1 : Objects.requireNonNull(file.listFiles())) {
                if (!file1.isDirectory()) {
                    usedSize += file1.length();
                } else {
                    usedSize += getUsedSize(file1.getAbsolutePath());
                }
            }
        }
        return usedSize;
    }

}
