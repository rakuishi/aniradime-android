package com.rakuishi.postalcode.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PostalCodeRepository implements PostalCodeDataSource {

    private final PostalCodeDataSource dataSource;

    @Inject
    public PostalCodeRepository(PostalCodeLocalDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
