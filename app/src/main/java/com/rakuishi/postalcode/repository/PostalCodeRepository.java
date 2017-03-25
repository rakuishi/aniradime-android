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

    @Override
    public Single<List<PostalCode>> findPrefectures() {
        return dataSource.findPrefectures();
    }

    @Override
    public Single<List<PostalCode>> findByPrefectureId(int prefectureId) {
        return dataSource.findByPrefectureId(prefectureId);
    }

    @Override
    public Single<List<PostalCode>> findByCityId(int cityId) {
        return dataSource.findByCityId(cityId);
    }
}
