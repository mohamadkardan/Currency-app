package com.example.currency.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object {

        private fun getRetrofit(BASE_URL: String): Retrofit {
            val convertorRetrofit: Retrofit by lazy {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder().addInterceptor(logging).build()

                val okHttpClient: OkHttpClient =
                    OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build()

                Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(client)
                    .build()
            }
            return convertorRetrofit
        }

        fun getApi(BASE_URL: String) = getRetrofit(BASE_URL).create(CurrencyAPI::class.java)

    }

}