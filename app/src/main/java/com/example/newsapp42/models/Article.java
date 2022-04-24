package com.example.newsapp42.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Article implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;
    private String date;

    public Article(String text, String date) {
        this.text = text;
        this.date = date;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
