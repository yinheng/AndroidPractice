package com.yh.boradcastriceiverdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    public static final Uri URI = Uri.parse("content://com.yh.broadcastriceiverdemo.mycontentprovidet");
    private SQLiteDatabase readDB;
    private SQLiteDatabase writeDB;

    @Override
    public boolean onCreate() {
        UserDB userDB = new UserDB(this.getContext());
        readDB = userDB.getReadableDatabase();
        writeDB = userDB.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return readDB.query(UserDB.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        writeDB.insert(UserDB.TABLE, null, values);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return writeDB.delete(UserDB.TABLE, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return writeDB.update(UserDB.TABLE, values, selection, selectionArgs);
    }
}
