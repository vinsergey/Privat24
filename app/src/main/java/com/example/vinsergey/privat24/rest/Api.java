package com.example.vinsergey.privat24.rest;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    String BASE_URL = "https://api.privatbank.ua";

    @GET("/p24api/pubinfo?exchange&json&coursid=5")
    Call<List<ModelCurrency>> getAllCurrency();
}
