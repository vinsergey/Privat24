package com.example.vinsergey.privat24;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.vinsergey.privat24.db.AppDatabase;
import com.example.vinsergey.privat24.db.Currency;
import com.example.vinsergey.privat24.db.CurrencyEntity;
import com.example.vinsergey.privat24.rest.ModelCurrency;
import com.example.vinsergey.privat24.rest.RecyclerViewAdapter;
import com.example.vinsergey.privat24.rest.RestClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    public static final String KEY_BUNDLE = "ccy";
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    public static String currentDateTime;

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
        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData() {
        if (hasConnection(this)) {
            RestClient.getInstance().getAllCurrency().enqueue(new Callback<List<ModelCurrency>>() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onResponse(@NonNull Call<List<ModelCurrency>> call, @NonNull Response<List<ModelCurrency>> response) {

                    TimeZone tz = TimeZone.getTimeZone("GMT+03:00");
                    Calendar c = Calendar.getInstance(tz);

                    currentDateTime = String.format("%02d", c.get(Calendar.DAY_OF_MONTH)) + "." +
                            String.format("%02d", c.get(Calendar.MONTH) + 1) + "." +
                            String.format("%02d", c.get(Calendar.YEAR)) + " " +
                            String.format("%02d", c.get(Calendar.HOUR_OF_DAY)) + ":" +
                            String.format("%02d", c.get(Calendar.MINUTE)) + ":" +
                            String.format("%02d", c.get(Calendar.SECOND));

                    AppDatabase.getInstance(MainActivity.this).currencyDao().saveCurrency(mapEntity(response));
                    adapter.setData(map(AppDatabase.getInstance(MainActivity.this).currencyDao().getLastCurrency()));
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(@NonNull Call<List<ModelCurrency>> call, @NonNull Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
            adapter.setData(map(AppDatabase.getInstance(MainActivity.this).currencyDao().getLastCurrency()));
        }
    }

    public static List<Currency> map(List<CurrencyEntity> currencies) {
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

    public static List<CurrencyEntity> mapEntity(Response<List<ModelCurrency>> currencies) {
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

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        Currency currency = (Currency) v.getTag();
        bundle.putString(KEY_BUNDLE, currency.ccy);
        Intent intent = new Intent(this, CurrencyActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clear_base) {
            AppDatabase.getInstance(MainActivity.this).currencyDao().deleteBase();
            getData();
        }

        if (id == R.id.start_service) {
            startService(new Intent(this, CurrencyService.class));
        }

        if (id == R.id.stop_service) {
            stopService(new Intent(this, CurrencyService.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifiInfo = Objects.requireNonNull(cm).getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        if (wifiInfo != null && wifiInfo.isConnected())
//        {
//            return true;
//        }
//        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//        if (wifiInfo != null && wifiInfo.isConnected())
//        {
//            return true;
//        }
        NetworkInfo wifiInfo = Objects.requireNonNull(cm).getActiveNetworkInfo();
        return wifiInfo != null && wifiInfo.isConnected();
    }
}
