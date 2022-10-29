package com.example.currency.viewmodel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.currency.R
import com.example.currency.databinding.FragmentHomeBinding
import com.example.currency.model.ConvertResponse
import com.example.currency.model.Currency
import com.example.currency.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.ArrayList
import kotlin.Exception

class HomeViewModel : ViewModel() {

    private var repository: CurrencyRepository? = null
    var convertorResultLiveData: MutableLiveData<String?>? = null
    var convertResultExceptionLiveData: MutableLiveData<Exception?>? = null
    var isGettingData: MutableLiveData<Boolean> = MutableLiveData(false)
    private var convertResultRequestJob: Job? = null

    init {
        repository = CurrencyRepository()
        convertorResultLiveData = MutableLiveData()
        convertResultExceptionLiveData = MutableLiveData()
    }

    fun getCurrencyList(): MutableList<Currency> = repository!!.getCurrencyList()

    private fun getCurrenciesCode(): MutableList<String> {
        val codes: MutableList<String> = ArrayList()
        for (currency in repository!!.getCurrencyList()) {
            codes.add(currency.code)
        }
        return codes
    }


    fun getSpinnerAdapter(context: Context): ArrayAdapter<String> {
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_item,
            getCurrenciesCode()
        )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return dataAdapter
    }

    fun getConvertResult(baseCode: String, targetCode: String, amount: String) {
        convertResultRequestJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                isGettingData.postValue(true)
                val response = repository?.getConvertResult(
                    baseCode = baseCode,
                    targetCode = targetCode,
                    amount = amount
                )
                convertorResultLiveData?.postValue(response?.body()?.conversion_result.toString())
            } catch (e: Exception) {
                isGettingData.postValue(false)
                convertResultExceptionLiveData?.postValue(e)
            }
        }
    }

    fun cancelConvertResultRequest() {
        convertResultRequestJob?.cancel()
        isGettingData.postValue(false)
    }

    fun navigateToCurrencyRateFragment(view: View, baseCode: String) {
        val bundle = Bundle()
        bundle.putString("baseCode", baseCode)
        Navigation.findNavController(view).navigate(R.id.rateFragment, bundle)
    }

}