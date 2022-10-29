package com.example.currency.api

import com.example.currency.model.ConvertResponse
import com.example.currency.model.RatesByBaseCodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface CurrencyAPI {

    @GET("pair/{baseCode}/{targetCode}/{amount}")
    suspend fun convertCurrency(
        @Path("baseCode") baseCode: String,
        @Path("targetCode") targetCode: String,
        @Path("amount") amount: String
    ): Response<ConvertResponse>

    @GET("latest/{baseCode}")
    suspend fun getRatesByBaseCode(@Path("baseCode") baseCode: String): Response<RatesByBaseCodeResponse>

}