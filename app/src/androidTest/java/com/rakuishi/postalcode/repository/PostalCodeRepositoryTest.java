package com.rakuishi.postalcode.repository;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.rakuishi.postalcode.R;
import com.rakuishi.postalcode.model.OrmaDatabase;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.util.UnzipDbUtil;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.android.schedulers.AndroidSchedulers;

import static org.junit.Assert.*;

public class PostalCodeRepositoryTest {

    private Context context;
    private PostalCodeRepository repository;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        UnzipDbUtil.unzipIfNecessary(
                context,
                context.getString(R.string.zipped_database_name),
                context.getString(R.string.database_name)
        );

        String name = context.getString(R.string.database_name);
        PostalCodeLocalDataSource dataSource =
                new PostalCodeLocalDataSource(OrmaDatabase.builder(context).name(name).build());

        repository = new PostalCodeRepository(dataSource);
    }

    @Test
    public void findAll() throws Exception {
        repository.findAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCodes, throwable) -> {
                    assertNotNull(postalCodes);

                    PostalCode postalCode = postalCodes.get(0);
                    assertEquals(postalCode.prefecture, "北海道");
                    assertEquals(postalCode.city, "札幌市中央区");
                });
    }
}