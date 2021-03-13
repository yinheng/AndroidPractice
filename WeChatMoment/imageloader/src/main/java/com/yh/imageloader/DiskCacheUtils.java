package com.yh.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DiskCacheUtils {
    private static DiskCacheUtils diskCacheUtils;
    private long maxSize = 0;
    private static String path;

    private DiskCacheUtils() {
    }

    public static DiskCacheUtils getInstance() {
        return diskCacheUtils;
    }

    public static void initDiskCache(Context context, String subPath) {
        path = context.getExternalCacheDir().getAbsolutePath() + "/" + subPath;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        if (diskCacheUtils == null) {
            diskCacheUtils = new DiskCacheUtils();
        }
    }

    public void reSizeByMB(int maxSize) {
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

    public void put(String key, Bitmap bitmap) {
        if (key == null && bitmap == null)
            return;

        String filePath = path + "/" + key;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteLastFile() {
        File file = new File(path);
        if (file.isDirectory()) {
            List<File> fileList = Arrays.stream(Objects.requireNonNull(file.listFiles())).sorted(new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return Long.compare(o2.lastModified(), o1.lastModified());
                }
            }).collect(Collectors.toList());

            fileList.remove(0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveFile(String key, Bitmap bitmap) {
        if (maxSize == 0) {
            put(key, bitmap);
            return;
        }
        long freeSize = getFreeSize();
        if (bitmap.getByteCount() > freeSize) {
            deleteLastFile();
            saveFile(key, bitmap);
        }
        put(key, bitmap);
    }

    private long getFreeSize() {
        long freeSize = 0;
        if (maxSize != 0) {
            long usedSize = getUsedSpace(path);
            if (maxSize <= usedSize) {
                freeSize = maxSize - usedSize;
            }
        }
        return freeSize;
    }

    private long getUsedSpace(String path) {
        long size = 0;
        File file = new File(path);
        if (file.isDirectory()) {
            for (File file1 : Objects.requireNonNull(file.listFiles())) {
                size += getUsedSpace(file1.getAbsolutePath());
            }
        } else {
            size = file.length();
        }
        return size;
    }

}
