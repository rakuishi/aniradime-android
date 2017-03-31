package com.rakuishi.postalcode.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BookmarkLocalDataSource implements BookmarkDataSource {

    private final String BOOKMARK_CODES = "codes";
    private final Context context;
    private final Gson gson;

    @Inject
    public BookmarkLocalDataSource(Context context, Gson gson) {
        this.context = context;
        this.gson = gson;
    }

    public void save(List<String> codes) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(BOOKMARK_CODES, gson.toJson(codes));
        editor.apply();
    }

    @Override
    public List<String> findAll() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(BOOKMARK_CODES, "");

        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }

        List<String> codes = new ArrayList<>();
        try {
            codes = gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
        } catch (JsonSyntaxException e) {
            // In this situation, this method returns new ArrayList
        }

        return codes;
    }

    @Override
    public void add(String code) {
        // FIXME: this method must consider SQLITE_LIMIT_VARIABLE_NUMBER = 999
        List<String> codes = findAll();
        codes.add(code);
        save(codes);
    }

    @Override
    public void remove(String code) {
        List<String> codes = findAll();
        codes.remove(code);
        save(codes);
    }

    @Override
    public void clear() {
        List<String> codes = findAll();
        codes.clear();
        save(codes);
    }
}
