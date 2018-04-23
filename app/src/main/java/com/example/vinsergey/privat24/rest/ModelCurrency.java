package com.example.vinsergey.privat24.rest;

import com.example.vinsergey.privat24.db.Currency;
import com.google.gson.annotations.SerializedName;

public class ModelCurrency {
    /**
     * ccy : USD
     * base_ccy : UAH
     * buy : 25.95000
     * sale : 26.31579
     */

    @SerializedName("ccy")
    public String ccy;
    @SerializedName("base_ccy")
    public String baseCcy;
    @SerializedName("buy")
    public String buy;
    @SerializedName("sale")
    public String sale;
}
