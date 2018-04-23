package com.example.vinsergey.privat24;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

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
    private ImageView leftFlag, rightFlag;

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

        leftFlag = findViewById(R.id.flag_left);
        rightFlag = findViewById(R.id.flag_right);

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
