package com.example.currency.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency.R
import com.example.currency.model.RatesByBaseCodeResponse
import com.example.currency.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RateViewModel : ViewModel() {

    var getRatesByBaseCodeJob: Job? = null
    var rateByBaseCodeLiveData: MutableLiveData<RatesByBaseCodeResponse>? = null
    var requestDataExceptionLiveData: MutableLiveData<Exception?>? = null

    init {
        rateByBaseCodeLiveData = MutableLiveData()
        requestDataExceptionLiveData = MutableLiveData()
    }

    fun getRatesByBaseCode(baseCode: String) {
        getRatesByBaseCodeJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = CurrencyRepository.getInstance().getRatesByBaseCode(baseCode = baseCode).body()
                rateByBaseCodeLiveData!!.postValue(response)
            } catch (e: Exception) {
                requestDataExceptionLiveData?.postValue(e)
                Log.d("TEST", "getConvertResult: ${e.message.toString()}")
            }
        }
    }

}