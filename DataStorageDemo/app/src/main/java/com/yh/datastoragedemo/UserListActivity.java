package com.yh.datastoragedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    private UserDB userDB;
    private EditText name, sex;
    private RecyclerViewAdapter adapter;
    private final List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_layout);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        name = findViewById(R.id.name);
        sex = findViewById(R.id.sex);
        userDB = new UserDB(this);

        List<User> userList = selectAll();
        adapter = new RecyclerViewAdapter(userList, userDB);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameStr = name.getText().toString();
                String sexStr = sex.getText().toString();
                insertData(nameStr, sexStr);
                adapter.notifyItemInserted(userList.size() - 1);
                name.setText("");
                sex.setText("");

            }
        });
    }

    private void insertData(String name, String sex) {
        SQLiteDatabase writeDB = userDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("sex", sex);
        writeDB.insert("user", null, contentValues);
        userList.add(new User(null, name, sex));

        writeDB.close();
    }

    private List<User> selectAll() {
        SQLiteDatabase readDB = userDB.getReadableDatabase();
        Cursor cursor = readDB.query("user", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            userList.add(new User(id, name, sex));
        }
        readDB.close();
        return userList;
    }
}
