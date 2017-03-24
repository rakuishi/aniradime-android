package com.rakuishi.postalcode.repository;

import com.rakuishi.postalcode.model.PostalCode;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class PostalCodeRepository implements PostalCodeDataSource {

    private final PostalCodeDataSource dataSource;

    @Inject
    public PostalCodeRepository(PostalCodeLocalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Single<List<PostalCode>> findAll() {
        return dataSource.findAll();
    }
}
