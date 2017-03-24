package com.rakuishi.postalcode.repository;

import com.rakuishi.postalcode.model.OrmaDatabase;

import javax.inject.Inject;

public class PostalCodeLocalDataSource implements PostalCodeDataSource {

    private final OrmaDatabase orma;

    @Inject
    public PostalCodeLocalDataSource(OrmaDatabase orma) {
        this.orma = orma;
    }
}
