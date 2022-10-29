package com.example.currency.model

import com.google.gson.annotations.SerializedName

data class ConversionRates(
    @SerializedName("IRR") val irr: Double,
    @SerializedName("USD") val usd: Double,
    @SerializedName("RUB") val rub: Double,
    @SerializedName("CNY") val cny: Double
)