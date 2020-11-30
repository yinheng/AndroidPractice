package com.yh.widgetdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class AtyUsingNotification extends AppCompatActivity {
    private NotificationManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_notification);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        findViewById(R.id.btnNotification).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AtyUsingNotification.this, AtyUsingNotification.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(AtyUsingNotification.this,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("1",
                            "my_channel", NotificationManager.IMPORTANCE_DEFAULT);
                    channel.enableLights(true); //是否在桌面icon右上角展示小红点
                    channel.setLightColor(Color.GREEN); //小红点颜色
                    channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                    channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(channel);
                }

                Notification n = new NotificationCompat.Builder(AtyUsingNotification.this, "1")
                        .setTicker("ticker")
                        .setContentTitle("通知")
                        .setContentText("你好")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setChannelId("1")
                        .setDefaults(Notification.DEFAULT_SOUND) //解决android旧版本通知没有铃声，设置默认铃声
                        .setContentIntent(pendingIntent)
                        .build();


                manager.notify(123, n);

            }
        });
    }
}
