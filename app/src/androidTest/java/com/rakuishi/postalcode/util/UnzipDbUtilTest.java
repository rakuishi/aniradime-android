package com.rakuishi.postalcode.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.rakuishi.postalcode.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UnzipDbUtilTest {

    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void unzipIfNecessary() {
        try {
            UnzipDbUtil.unzipIfNecessary(
                    context,
                    context.getString(R.string.zipped_database_name),
                    context.getString(R.string.database_name)
            );
        } catch (IOException | SecurityException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void deleteDbFromDbPath() {
        try {
            UnzipDbUtil.deleteDbFromDbPath(
                    context,
                    context.getString(R.string.database_name)
            );
        } catch (SecurityException e) {
            fail(e.getMessage());
        }
    }
}