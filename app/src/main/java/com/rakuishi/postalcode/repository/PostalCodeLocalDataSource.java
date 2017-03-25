package com.rakuishi.postalcode.repository;

import android.database.Cursor;

import com.rakuishi.postalcode.model.OrmaDatabase;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.model.PostalCode_Relation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
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

    @Override
    public Single<List<PostalCode>> findPrefectures() {
        List<PostalCode> prefectures = new ArrayList<>();

        Cursor cursor = orma.getConnection().rawQuery("SELECT DISTINCT prefecture_id, prefecture FROM postalcode;");
        while (cursor.moveToNext()) {
            PostalCode postalCode = new PostalCode();
            postalCode.prefectureId = cursor.getInt(cursor.getColumnIndex("prefecture_id"));
            postalCode.prefecture = cursor.getString(cursor.getColumnIndex("prefecture"));
            prefectures.add(postalCode);
        }
        cursor.close();

        return Single.just(prefectures);
    }

    @Override
    public Single<List<PostalCode>> findByPrefectureId(int prefectureId) {
        return postalCodes().selector().prefectureIdEq(prefectureId).executeAsObservable().toList();
    }

    @Override
    public Single<List<PostalCode>> findByCityId(int cityId) {
        return postalCodes().selector().prefectureIdEq(cityId).executeAsObservable().toList();
    }

}
