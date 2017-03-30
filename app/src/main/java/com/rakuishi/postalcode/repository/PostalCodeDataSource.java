package com.rakuishi.postalcode.repository;

import com.rakuishi.postalcode.model.PostalCode;

import java.util.List;

import io.reactivex.Single;

public interface PostalCodeDataSource {

    Single<List<PostalCode>> findAll();
    Single<List<PostalCode>> findPrefectures();
    Single<List<PostalCode>> findByPrefectureId(int prefectureId);
    Single<List<PostalCode>> findByCityId(int cityId);
    Single<List<PostalCode>> find(String query);
}
