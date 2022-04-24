package com.example.newsapp42.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private final SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void savedBoardState() {
        preferences.edit().putBoolean("isBoardShown", true).apply();
    }

    public boolean isBoardShown() {
        return preferences.getBoolean("isBoardShown", false);
    }

    public void saveImage(String image) {
        preferences.edit().putString("A2", image).apply();
    }

    public String getImageUri() {
        return preferences.getString("A2", null);
    }
}
