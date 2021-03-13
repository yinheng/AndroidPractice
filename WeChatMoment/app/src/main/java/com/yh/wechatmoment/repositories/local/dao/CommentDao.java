package com.yh.wechatmoment.repositories.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yh.wechatmoment.model.Comment;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("select * from comment where tweetId = :tweetId")
    List<Comment> findCommentByTweetId(String tweetId);

    @Query("select * from comment where commentId = :commentId")
    List<Comment> findCommentByCommentId(String commentId);

    @Insert
    void insert(Comment comment);

    @Update
    void update(Comment comment);

    @Query("delete from comment")
    void deleteAll();


}
