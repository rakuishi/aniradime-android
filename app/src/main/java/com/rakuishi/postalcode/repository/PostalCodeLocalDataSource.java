package com.rakuishi.postalcode.repository;

import android.database.Cursor;

import com.rakuishi.postalcode.model.OrmaDatabase;
import com.rakuishi.postalcode.model.PostalCode;
import com.rakuishi.postalcode.model.PostalCode_Relation;

import java.util.ArrayList;
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

    @Override
    public Single<List<PostalCode>> findPrefectures() {
        List<PostalCode> prefectures = new ArrayList<>();

        Cursor cursor = orma.getConnection().rawQuery("SELECT DISTINCT prefecture_id, prefecture, prefecture_yomi FROM postalcode;");
        while (cursor.moveToNext()) {
            PostalCode postalCode = new PostalCode();
            postalCode.prefectureId = cursor.getInt(cursor.getColumnIndex("prefecture_id"));
            postalCode.prefecture = cursor.getString(cursor.getColumnIndex("prefecture"));
            postalCode.prefectureYomi = cursor.getString(cursor.getColumnIndex("prefecture_yomi"));
            prefectures.add(postalCode);
        }
        cursor.close();

        return Single.just(prefectures);
    }

    @Override
    public Single<List<PostalCode>> findByPrefectureId(int prefectureId) {
        List<PostalCode> cities = new ArrayList<>();

        Cursor cursor = orma.getConnection().rawQuery(
                "SELECT DISTINCT city_id, city, city_yomi FROM postalcode WHERE prefecture_id = ?;",
                String.valueOf(prefectureId)
        );
        while (cursor.moveToNext()) {
            PostalCode postalCode = new PostalCode();
            postalCode.cityId = cursor.getInt(cursor.getColumnIndex("city_id"));
            postalCode.city = cursor.getString(cursor.getColumnIndex("city"));
            postalCode.cityYomi = cursor.getString(cursor.getColumnIndex("city_yomi"));
            cities.add(postalCode);
        }
        cursor.close();

        return Single.just(cities);
    }

    @Override
    public Single<List<PostalCode>> findByCityId(int cityId) {
        return postalCodes().selector().cityIdEq(cityId).streetNotEq("").executeAsObservable().toList();
    }

    @Override
    public Single<List<PostalCode>> find(String query) {
        List<PostalCode> postalCodes = new ArrayList<>();

        // 福岡県福岡市 → 福岡県 福岡市
        String[] queries = query
                .replace("-", "")
                .replace("都", "都 ")
                .replace("道", "道 ")
                .replace("府", "府 ")
                .replace("県", "県 ")
                .replace("市", "市 ")
                .replace("町", "町 ")
                .replace("村", "村 ")
                .replace("区", "区 ")
                .replace("郡", "郡 ")
                .split(" ", -1);

        Cursor cursor = orma.getConnection().rawQuery(
                "SELECT * FROM postalcode WHERE code LIKE '%' || ? || '%'"
                + " OR prefecture LIKE '%' || ? || '%' "
                + " OR prefecture_yomi LIKE '%' || ? || '%' "
                + " OR city LIKE '%' || ? || '%' "
                + " OR city_yomi LIKE '%' || ? || '%' "
                + " OR street LIKE '%' || ? || '%' "
                + " OR street_yomi LIKE '%' || ? || '%' ;",
                queries[0],
                queries[0],
                queries[0],
                queries[0],
                queries[0],
                queries[0],
                queries[0]
        );

        while (cursor.moveToNext()) {
            PostalCode postalCode = new PostalCode();
            postalCode.code = cursor.getString(cursor.getColumnIndex("code"));
            postalCode.prefectureId = cursor.getInt(cursor.getColumnIndex("prefecture_id"));
            postalCode.prefecture = cursor.getString(cursor.getColumnIndex("prefecture"));
            postalCode.prefectureYomi = cursor.getString(cursor.getColumnIndex("prefecture_yomi"));
            postalCode.cityId = cursor.getInt(cursor.getColumnIndex("city_id"));
            postalCode.city = cursor.getString(cursor.getColumnIndex("city"));
            postalCode.cityYomi = cursor.getString(cursor.getColumnIndex("city_yomi"));
            postalCode.street = cursor.getString(cursor.getColumnIndex("street"));
            postalCode.streetYomi = cursor.getString(cursor.getColumnIndex("street_yomi"));
            postalCodes.add(postalCode);
        }
        cursor.close();

        for (int i = 1; i < queries.length; i++) {
            List<PostalCode> temp = new ArrayList<>();
            for (PostalCode postalCode: postalCodes) {
                if (postalCode.contains(queries[i])) {
                    temp.add(postalCode);
                }
            }

            if (temp.size() == 0) {
                break;
            } else {
                postalCodes = temp;
            }
        }

        return Single.just(postalCodes);
    }

    @Override
    public Single<PostalCode> findByCode(String code) {
        return postalCodes().selector().codeEq(code).executeAsObservable().firstOrError();
    }

    @Override
    public Single<List<PostalCode>> findByCodes(ArrayList<String> codes) {
        return postalCodes().selector().codeIn(codes).executeAsObservable().toList();
    }
}
