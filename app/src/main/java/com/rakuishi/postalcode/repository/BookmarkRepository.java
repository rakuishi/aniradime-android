package com.rakuishi.postalcode.repository;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BookmarkRepository implements BookmarkDataSource {

    private final BookmarkDataSource dataSource;

    @Inject
    public BookmarkRepository(BookmarkLocalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList<String> findAll() {
        return dataSource.findAll();
    }

    @Override
    public boolean exists(String code) {
        return dataSource.exists(code);
    }

    @Override
    public void add(String code) throws IllegalArgumentException, IllegalStateException {
        dataSource.add(code);
    }

    @Override
    public void remove(String code) {
        dataSource.remove(code);
    }

    @Override
    public void clear() {
        dataSource.clear();
    }
}
