package com.rakuishi.postalcode.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

@Table("postalcode")
public class PostalCode {

    @Column(value = "code", indexed = true)
    public String code;

    @Column(value = "prefecture_id", indexed = true)
    public int prefectureId;

    @Column(value = "city_id", indexed = true)
    public int cityId;

    @Column(value = "prefecture", indexed = true)
    public String prefecture;

    @Column(value = "city", indexed = true)
    public String city;

    @Column(value = "street", indexed = true)
    public String street;

    @Column("prefecture_yomi")
    public String prefectureYomi;

    @Column("city_yomi")
    public String cityYomi;

    @Column("street_yomi")
    public String streetYomi;
}