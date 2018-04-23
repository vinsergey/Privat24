package com.example.vinsergey.privat24.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveCurrency(List<CurrencyEntity> currencyEntities);

    //@Query("select * from currency_table where id = (select max(id) from currency_table)")
    @Query("SELECT * FROM currency_table order by ID limit 4")
    List<CurrencyEntity> getLastCurrency();

    @Query("SELECT * FROM currency_table")
    List<CurrencyEntity> getAllCyrrency();
}
