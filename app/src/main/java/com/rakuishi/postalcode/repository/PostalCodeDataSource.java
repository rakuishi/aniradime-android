package com.rakuishi.postalcode.repository;

import com.rakuishi.postalcode.model.PostalCode;

import java.util.List;

import io.reactivex.Single;

public interface PostalCodeDataSource {

    Single<List<PostalCode>> findAll();
}
