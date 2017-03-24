package com.rakuishi.postalcode.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

@Table("postalcode")
public class PostalCode {

    @Column("prefecture_id")
    public String prefectureId;

    @Column("city_id")
    public String cityId;

    @Column("code")
    public String code;

    @Column("prefecture")
    public String prefecture;

    @Column("city")
    public String city;

    @Column("street")
    public String street;

    @Column("prefecture_yomi")
    public String prefectureYomi;

    @Column("city_yomi")
    public String cityYomi;

    @Column("street_yomi")
    public String streetYomi;
}
