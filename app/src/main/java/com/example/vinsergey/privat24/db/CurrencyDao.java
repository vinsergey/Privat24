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

    @Query("SELECT * FROM (SELECT * FROM currency_table order by id desc limit 4) AS T ORDER BY id asc")
    List<CurrencyEntity> getLastCurrency();

    @Query("SELECT * FROM currency_table where ccy = 'USD' ORDER BY id DESC")
    List<CurrencyEntity> getUSDCyrrency();

    @Query("SELECT * FROM currency_table where ccy = 'EUR' ORDER BY id DESC")
    List<CurrencyEntity> getEURCyrrency();

    @Query("SELECT * FROM currency_table where ccy = 'RUR' ORDER BY id DESC")
    List<CurrencyEntity> getRURCyrrency();

    @Query("SELECT * FROM currency_table where ccy = 'BTC' ORDER BY id DESC")
    List<CurrencyEntity> getBTCCyrrency();
}
