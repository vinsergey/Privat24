package com.example.vinsergey.privat24.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "currency_table")
public class CurrencyEntity {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    @ColumnInfo(name = "ccy")
    public String ccy_entity;
    @ColumnInfo(name = "base_ccy")
    public String base_ccy_entity;
    @ColumnInfo(name = "bye")
    public String bye_entity;
    @ColumnInfo(name = "sale")
    public String sale_entity;
    @ColumnInfo(name = "date_time")
    public String dateTimeEntity;
}
