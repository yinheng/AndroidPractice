package com.yh.boradcastriceiverdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AtyUsingContentProvider extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_using_contentprovider);
        findViewById(R.id.btnGetContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,null, null,null);
//                while (cursor.moveToNext()){
//                    Log.e("contact", cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
//                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "小明");
                contentValues.put("age", 15);
                getContentResolver().insert(MyContentProvider.URI, contentValues);

                Cursor cursor = getContentResolver().query(MyContentProvider.URI, null, null, null, null);
                while (cursor.moveToNext()) {
                    Log.e("id", "" + cursor.getInt(cursor.getColumnIndex("id")));
                    Log.e("name", cursor.getString(cursor.getColumnIndex("name")));
                    Log.e("age", "" + cursor.getInt(cursor.getColumnIndex("age")));
                }
                Log.e("cursor count", "" + cursor.getCount());

                getContentResolver().delete(MyContentProvider.URI, "id = ?", new String[]{"" + (cursor.getCount() - 1)});

                cursor.close();
            }
        });
    }
}
