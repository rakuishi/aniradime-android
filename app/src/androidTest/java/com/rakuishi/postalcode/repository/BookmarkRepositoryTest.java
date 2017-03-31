package com.rakuishi.postalcode.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookmarkRepositoryTest {

    private BookmarkRepository repository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Gson gson = new Gson();

        BookmarkLocalDataSource dataSource =
                new BookmarkLocalDataSource(context, gson);

        repository = new BookmarkRepository(dataSource);
        repository.clear();
    }

    @Test
    public void add() throws Exception {
        repository.add("8000000");
        assertEquals(repository.findAll().size(), 1);

        try {
            repository.add("123456");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getClass(), IllegalArgumentException.class);
        }

        repository.clear();
        for (int i = 1; i <= 1000; i++) {
            try {
                repository.add("800" + String.format("%04d", i));
            } catch (IllegalStateException e) {
                assertEquals(e.getClass(), IllegalStateException.class);
            }
        }
    }
}