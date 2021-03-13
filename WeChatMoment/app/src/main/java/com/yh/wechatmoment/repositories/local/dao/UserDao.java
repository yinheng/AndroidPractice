package com.yh.wechatmoment.repositories.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yh.wechatmoment.model.User;

@Dao
public interface UserDao {

    @Query("select * from user")
    User getUser();

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);


}
