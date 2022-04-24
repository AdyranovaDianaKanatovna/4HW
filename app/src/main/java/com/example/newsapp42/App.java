package com.example.newsapp42;

import android.app.Application;

import androidx.room.Room;

import com.example.newsapp42.room.AppDatabase;

public class App extends Application {
    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = Room
                .databaseBuilder(this, AppDatabase.class, "database.db")
                .allowMainThreadQueries()
                .build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
