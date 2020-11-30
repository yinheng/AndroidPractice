package com.yh.widgetdemo;


import android.content.Context;
import android.content.Intent;

public class ListViewCellData {
    private String content;
    private Context context;
    private Intent intent;

    public ListViewCellData(String content, Context context, Intent intent) {
        this.content = content;
        this.context = context;
        this.intent = intent;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public Context getContext() {
        return context;
    }

    public Intent getIntent() {
        return intent;
    }

    public void startActivity() {
        context.startActivity(intent);
    }

    @Override
    public String toString() {
        return getcontent();
    }
}
