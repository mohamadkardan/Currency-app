package com.example.currency.repository

import androidx.lifecycle.MutableLiveData
import com.example.currency.R
import com.example.currency.api.RetrofitInstance
import com.example.currency.model.ConvertResponse
import com.example.currency.model.Currency
import com.example.currency.model.RatesByBaseCodeResponse
import com.example.currency.util.Constants
import retrofit2.Response
import java.util.ArrayList

class CurrencyRepository private constructor(){

    companion object {

        private var INSTANCE: CurrencyRepository? = null

        fun getInstance(): CurrencyRepository {
            if (INSTANCE == null) {
                INSTANCE = CurrencyRepository()
            }
            return INSTANCE!!
        }

    }

    suspend fun getConvertResult(
        baseCode: String,
        targetCode: String,
        amount: String
    ): Response<ConvertResponse> {
        return RetrofitInstance.getApi(Constants.CONVERTOR_BASE_URL)
            .convertCurrency(baseCode = baseCode, targetCode = targetCode, amount = amount)
    }

    suspend fun getRatesByBaseCode(baseCode: String): Response<RatesByBaseCodeResponse> =
        RetrofitInstance.getApi(Constants.CONVERTOR_BASE_URL)
            .getRatesByBaseCode(baseCode = baseCode)

    fun getCurrencyList(): MutableList<Currency> {
        val list: MutableList<Currency> = ArrayList()
        list.add(Currency("ریال ایران", "IRR", R.drawable.ic_flag_iran))
        list.add(Currency("دلار آمریکا", "USD", R.drawable.ic_flag_united_states))
        list.add(Currency("روبل روسیه", "RUB", R.drawable.ic_flag_russia))
        list.add(Currency("یوآن چین", "CNY", R.drawable.ic_flag_china))
        return list
    }

}
