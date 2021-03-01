package com.yh.wechatmoments.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yh.wechatmoments.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from user")
    List<User> getAllUsers();

    @Query("select * from user where id = :id")
    User getUser(int id);

    @Insert
    void insertUser(User u);

    @Delete
    void delete(User u);
}
