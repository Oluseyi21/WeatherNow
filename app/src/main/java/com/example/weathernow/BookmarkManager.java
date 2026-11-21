package com.example.weathernow;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class BookmarkManager {
    private static final String PREF_NAME = "weather_bookmarks";
    private static final String KEY_CITIES = "saved_cities";
    private SharedPreferences preferences;
    public BookmarkManager(Context context) {
        preferences = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void addBookmark(String city) {
        Set<String> savedCities = getBookmarks();
        savedCities.add(city);
        preferences.edit()
                .putStringSet(KEY_CITIES, savedCities)
                .apply();
    }

    public void removeBookmark(String city) {
        Set<String> savedCities = getBookmarks();
        savedCities.remove(city);
        preferences.edit()
                .putStringSet(KEY_CITIES, savedCities)
                .apply();
    }

    public boolean isBookmarked(String city) {
        return getBookmarks().contains(city);
    }

    public Set<String> getBookmarks() {
        Set<String> savedCities = preferences.getStringSet(
                KEY_CITIES, new HashSet<>());
        return new HashSet<>(savedCities);
    }
}
