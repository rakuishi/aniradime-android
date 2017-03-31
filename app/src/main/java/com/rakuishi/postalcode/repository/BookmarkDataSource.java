package com.rakuishi.postalcode.repository;

import java.util.List;

import io.reactivex.Single;

public interface BookmarkDataSource {

    List<String> findAll();
    boolean exists(String code);
    void add(String code) throws IllegalArgumentException, IllegalStateException;
    void remove(String code);
    void clear();
}
