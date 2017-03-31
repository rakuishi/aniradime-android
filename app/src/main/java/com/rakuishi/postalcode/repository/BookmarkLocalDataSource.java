package com.rakuishi.postalcode.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean exists(List<String> codes, String code) {
        for (String comparedCode: codes) {
            if (TextUtils.equals(comparedCode, code)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public @NonNull ArrayList<String> findAll() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(BOOKMARK_CODES, "");

        if (TextUtils.isEmpty(json)) {
            return new ArrayList<>();
        }

        ArrayList<String> codes = new ArrayList<>();
        try {
            codes = gson.fromJson(json, new TypeToken<List<String>>(){}.getType());
        } catch (JsonSyntaxException e) {
            // In this situation, this method returns new ArrayList
        }

        return codes;
    }

    @Override
    public boolean exists(String code) {
        return exists(findAll(), code);
    }

    @Override
    public void add(String code) throws IllegalArgumentException, IllegalStateException {
        final Matcher matcher = Pattern.compile("\\d{7}+").matcher(code);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Code must fulfill a `\\d{7}+` pattern");
        }

        List<String> codes = findAll();
        if (exists(codes, code)) {
            return;
        }

        if (codes.size() == 999) {
            throw new IllegalStateException("Bookmark's limit is 999");
        }
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
