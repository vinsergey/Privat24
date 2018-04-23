package com.example.vinsergey.privat24;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.vinsergey.privat24.db.AppDatabase;
import com.example.vinsergey.privat24.db.Currency;
import com.example.vinsergey.privat24.db.CurrencyEntity;
import com.example.vinsergey.privat24.rest.ModelCurrency;
import com.example.vinsergey.privat24.rest.RecyclerViewAdapter;
import com.example.vinsergey.privat24.rest.RestClient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private String currentDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton actionButton = findViewById(R.id.fab);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+380938833136")));
            }
        });

        refreshLayout = findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        getData();
    }


    private void getData(){
        RestClient.getInstance().getAllCurrency().enqueue(new Callback<List<ModelCurrency>>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onResponse(@NonNull Call<List<ModelCurrency>> call, @NonNull Response<List<ModelCurrency>> response) {

//                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//                Date date = new Date();
                //currentDateTime = dateFormat.format(date);

                Calendar cal = Calendar.getInstance(); //.getInstance(new Locale("ru_RU@calendar=buddhist"));
                currentDateTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(cal.getTime());

                AppDatabase.getInstance(MainActivity.this).currencyDao().saveCurrency(mapEntity(response));
                adapter.setData(map(AppDatabase.getInstance(MainActivity.this).currencyDao().getLastCurrency()));
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<ModelCurrency>> call, @NonNull Throwable t) {

            }
        });
    }

    private List<Currency> map(List<CurrencyEntity> currencies) {
        List<Currency> list = new ArrayList<>();
        for (CurrencyEntity item : currencies) {
            Currency currency = new Currency();
            currency.ccy = item.ccy_entity;
            currency.base_ccy = item.base_ccy_entity;
            currency.bye = item.bye_entity;
            currency.sale = item.sale_entity;
            currency.dateTime = item.dateTimeEntity;
            list.add(currency);
        }
        return list;
    }

    private List<CurrencyEntity> mapEntity(Response<List<ModelCurrency>> currencies) {
        List<CurrencyEntity> list = new ArrayList<>();
        for (ModelCurrency item : Objects.requireNonNull(currencies.body())) {
            CurrencyEntity currencyEntity = new CurrencyEntity();
            currencyEntity.ccy_entity = item.ccy;
            currencyEntity.base_ccy_entity = item.baseCcy;
            currencyEntity.bye_entity = item.buy;
            currencyEntity.sale_entity = item.sale;
            currencyEntity.dateTimeEntity = currentDateTime;
            list.add(currencyEntity);
        }
        return list;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getData();
    }
}
