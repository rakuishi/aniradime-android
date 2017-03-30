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
import timber.log.Timber;

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
                    PostalCode postalCode = postalCodes.get(0);
                    assertEquals(postalCode.prefecture, "北海道");
                    assertEquals(postalCode.city, "札幌市中央区");
                });
    }

    @Test
    public void findByPrefectureId() throws Exception {
        repository.findByPrefectureId(40)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCodes, throwable) -> {
                    // PostalCode postalCode = postalCodes.get(0);
                    // assertEquals(postalCode.prefectureId, 40);
                    // assertEquals(postalCode.prefecture, "福岡県");
                });
    }

    @Test
    public void findByCityId() throws Exception {
        repository.findByCityId(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCodes, throwable) -> {
                    PostalCode postalCode = postalCodes.get(0);
                    assertEquals(postalCode.cityId, 1);
                    assertEquals(postalCode.city, "札幌市中央区");
                });
    }

    @Test
    public void findPrefectures() throws Exception {
        repository.findPrefectures()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCodes, throwable) -> {
                    assertEquals(postalCodes.size(), 47);
                });
    }

    @Test
    public void find() throws Exception {
        repository.find("0640941")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((postalCodes, throwable) -> {
                    assertEquals(postalCodes.size(), 1);
                });
    }
}