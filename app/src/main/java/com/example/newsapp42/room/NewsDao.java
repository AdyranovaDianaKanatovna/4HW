package com.example.newsapp42.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.newsapp42.models.Article;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM article")
    List<Article> getAll();

    @Insert
    void insert(Article article);

    @Delete
    void delete(Article article);

    @Query("SELECT * FROM article ORDER BY text ASC")
    List<Article> sort();

    @Query("SELECT * FROM article WHERE text LIKE '%' || :search || '%'")
    List<Article> getSearch(String search);
}
