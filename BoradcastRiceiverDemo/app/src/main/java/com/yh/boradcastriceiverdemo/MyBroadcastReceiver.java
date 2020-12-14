package com.yh.boradcastriceiverdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public final String ACTION = "com.yh.boradcastreceiverdemo.intent.action.mybcr";
    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getStringExtra("text");
        Log.e("MyBroadcastReceiver", text);
    }
}
