package com.rakuishi.postalcode.repository;

import java.util.List;

public interface BookmarkDataSource {

    List<String> findAll();
    boolean exists(String code);
    void add(String code) throws IllegalArgumentException, IllegalStateException;
    void remove(String code);
    void clear();
}
