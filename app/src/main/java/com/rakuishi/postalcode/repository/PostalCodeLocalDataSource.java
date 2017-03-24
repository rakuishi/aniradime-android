package com.rakuishi.postalcode.repository;

import com.rakuishi.postalcode.model.OrmaDatabase;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.model.PostalCode_Relation;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class PostalCodeLocalDataSource implements PostalCodeDataSource {

    private final OrmaDatabase orma;

    @Inject
    public PostalCodeLocalDataSource(OrmaDatabase orma) {
        this.orma = orma;
    }

    private PostalCode_Relation postalCodes() {
        return orma.relationOfPostalCode();
    }

    @Override
    public Single<List<PostalCode>> findAll() {
        return postalCodes().selector().executeAsObservable().toList();
    }
}
