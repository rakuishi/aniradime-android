package com.rakuishi.postalcode.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BookmarkRepositoryTest {

    private BookmarkRepository repository;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Gson gson = new Gson();

        BookmarkLocalDataSource dataSource =
                new BookmarkLocalDataSource(context, gson);

        repository = new BookmarkRepository(dataSource);
    }

    @Test
    public void add() throws Exception {
        repository.add("8000000");
        assertEquals(repository.findAll().size(), 1);
    }
}