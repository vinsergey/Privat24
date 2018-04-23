package com.example.vinsergey.privat24.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {CurrencyEntity.class})
public abstract class AppDatabase extends RoomDatabase {
    abstract public CurrencyDao currencyDao();

    private static AppDatabase INSTANCE;

    private static final Object OBJECT = new Object();

    public static AppDatabase getInstance(Context context) {
        synchronized (OBJECT) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "Currency.db")
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }
}
