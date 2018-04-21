package com.example.vinsergey.privat24;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.TextView;

import com.example.vinsergey.privat24.rest.ModelCurrency;
import com.example.vinsergey.privat24.rest.RecyclerViewAdapter;
import com.example.vinsergey.privat24.rest.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private List<ModelCurrency> modelCurrencyList;
    private RecyclerViewAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.swipe);
        refreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData(){
        RestClient.getInstance().getAllCurrency().enqueue(new Callback<List<ModelCurrency>>() {
            @Override
            public void onResponse(@NonNull Call<List<ModelCurrency>> call, @NonNull Response<List<ModelCurrency>> response) {
                modelCurrencyList = response.body();
                adapter.setData(modelCurrencyList);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<ModelCurrency>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getData();
    }
}
