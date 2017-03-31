package com.rakuishi.postalcode.repository;

import java.util.List;

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
    public List<String> findAll() {
        return dataSource.findAll();
    }

    @Override
    public void add(String code) {
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
