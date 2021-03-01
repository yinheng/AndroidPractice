package com.yh.imageloaderdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DiskCacheUtil {

  private static DiskCacheUtil diskCacheUtil;
  private Integer maxSize;
  private static String cachePath;

  private DiskCacheUtil() {
  }

  public static void init(Context context, String path) {
    File file = new File(context.getExternalCacheDir().getAbsoluteFile() + "/" + path);
    if (!file.exists()) {
      file.mkdir();
    }
    cachePath = file.getAbsolutePath();
    diskCacheUtil = new DiskCacheUtil();
  }

  public static DiskCacheUtil getInstance() {
    if (diskCacheUtil == null) {
      throw new IllegalStateException("XXX");
    }
    return diskCacheUtil;
  }

  /*param maxSize 单位为MB
   */
  public void setCacheSizeForMB(Integer maxSize) {
    this.maxSize = maxSize;
  }

  public Bitmap get(String key) {
    Bitmap bitmap = null;
    File file = new File(cachePath + "/" + key);
    if (file.exists()) {
      bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
    }
    return bitmap;
  }

  public Bitmap put(String key, Bitmap bitmap) {
    File file = new File(cachePath + "/" + key);

    try {
      if (bitmap != null) {
        if (!file.exists()) {
          file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  @RequiresApi(api = VERSION_CODES.N)
  public void putImgToDisk(String key, Bitmap bitmap) {
    if (maxSize != null) {
      long freeSpace = getFreeSpace();
      int bitmapSize = bitmap.getByteCount();
      if (bitmapSize > freeSpace) {
        deleteFile();
        putImgToDisk(key, bitmap);
      }
    }
    put(key, bitmap);
  }

  @RequiresApi(api = VERSION_CODES.N)
  private List<File> sortFilesByLastModified() {
    File file = new File(cachePath);
    List<File> fileList = Arrays.stream(Objects.requireNonNull(file.listFiles()))
        .sorted(new Comparator<File>() {
          @Override
          public int compare(File o1, File o2) {
            int result = 0;
            long o1Time = o1.lastModified();
            long o2Time = o2.lastModified();
            if (o1Time < o2Time) {
              result = -1;
            } else if (o1Time > o2Time) {
              result = 1;
            }
            return result;
          }
        }).collect(Collectors.toList());
    return fileList;
  }

  @RequiresApi(api = VERSION_CODES.N)
  private void deleteFile() {
    List<File> fileList = sortFilesByLastModified();
    if (fileList == null || fileList.isEmpty()) {
      return;
    }
    fileList.get(0).delete();
  }

  private long getFreeSpace() {
    long freeSpace = 0;
    if (maxSize != null) {
      long usedSpace = getUsedSpace(new File(cachePath));
      freeSpace = maxSize.longValue() * 1024 * 1024 - usedSpace;
    }
    return freeSpace;
  }

  private long getUsedSpace(File file) {
    long size = 0;
    if (file == null || !file.exists()) {
      return size;
    }

    if (!file.isDirectory()) {
      size = file.length();
      return size;
    }

    File[] files = file.listFiles();
    for (File subFile : files) {
      if (subFile.isFile()) {
        size += subFile.length();
      } else {
        size += getUsedSpace(subFile);
      }
    }
    return size;
  }
}
