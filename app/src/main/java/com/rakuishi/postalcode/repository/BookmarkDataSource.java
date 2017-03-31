package com.rakuishi.postalcode.repository;

import java.util.List;

import io.reactivex.Single;

public interface BookmarkDataSource {

    List<String> findAll();
    void add(String code);
    void remove(String code);
    void clear();
}
