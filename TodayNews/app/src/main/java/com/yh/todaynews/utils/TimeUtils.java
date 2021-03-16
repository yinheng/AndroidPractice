package com.yh.todaynews.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class TimeUtils {
    public static String timeFormat(String time) {

        StringBuilder currentDate = new StringBuilder();

        // 2021-03-14 09:42:00
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long timeMillis = Objects.requireNonNull(sdf.parse(time)).getTime();
            Log.e("TimeUtils", "timeMillis：" + timeMillis);

            long currentTime = System.currentTimeMillis();
            Log.e("TimeUtils", "currentTime：" + currentTime);

            long between = (currentTime - timeMillis) / 1000; //除以1000是为了转换成秒

            long day = between / (24 * 3600);
            long hour = between % (24 * 3600) / 3600;
            long minute = between % 3600 / 60;

            if (day != 0) {
                currentDate.append(day).append("天");
            }
            if (hour != 0) {
                currentDate.append(hour).append("小时");
            }
            if (minute != 0) {
                currentDate.append(minute).append("分钟");
            }
            if (currentDate.length() != 0) {
                currentDate.append("前");
            }
            Log.e("TimeUtils", "时间差 = ：" + currentDate.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentDate.toString();
    }
}
