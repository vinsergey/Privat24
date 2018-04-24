package com.example.vinsergey.privat24;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.vinsergey.privat24.db.AppDatabase;
import com.example.vinsergey.privat24.rest.RecyclerViewAdapter;
import java.util.Objects;
import static com.example.vinsergey.privat24.MainActivity.map;

public class CurrencyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        RecyclerView recyclerView = findViewById(R.id.recycler_one_currency);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        String currency = Objects.requireNonNull(bundle).getString(MainActivity.KEY_BUNDLE);
        assert currency != null;
        switch (currency) {
            case "USD":
                adapter.setData(map(AppDatabase.getInstance(CurrencyActivity.this).currencyDao().getUSDCyrrency()));
                break;
            case "EUR":
                adapter.setData(map(AppDatabase.getInstance(CurrencyActivity.this).currencyDao().getEURCyrrency()));
                break;
            case "RUR":
                adapter.setData(map(AppDatabase.getInstance(CurrencyActivity.this).currencyDao().getRURCyrrency()));
                break;
            case "BTC":
                adapter.setData(map(AppDatabase.getInstance(CurrencyActivity.this).currencyDao().getBTCCyrrency()));
                break;
                default: break;
        }
    }


}
