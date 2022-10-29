package com.example.currency.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.currency.R
import com.example.currency.databinding.FragmentRateBinding
import com.example.currency.util.ExceptionHandler
import com.example.currency.viewmodel.RateViewModel

class RateFragment : BaseFragment(R.layout.fragment_rate) {

    private lateinit var binding: FragmentRateBinding
    lateinit var rateViewModel: RateViewModel
    var baseCurrencyCode: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRateBinding.bind(view)
        binding.baseCode = requireArguments().getString("baseCode")
        initViewModel()
        setOnClicks()
        requestToGetData()
        observeRatesData()
        observeRequestDataException()
    }

    override fun initViewModel() {
        rateViewModel = ViewModelProvider(this)[RateViewModel::class.java]
    }

    private fun requestToGetData() {
        baseCurrencyCode = requireArguments().getString("baseCode")
        rateViewModel.getRatesByBaseCode(baseCurrencyCode!!)
    }

    private fun observeRatesData() {
        rateViewModel.rateByBaseCodeLiveData!!.observe(viewLifecycleOwner) {
            if (it != null) {
                regulateRatesList()
                binding.ratesByBaseCodeModel = it
                binding.progressbarLoadingFragmentRate.visibility = GONE
                binding.textViewLoadingFragmentRate.visibility = GONE
                binding.cardViewRatesFragmentRate.visibility = VISIBLE
            }
        }
    }

    private fun observeRequestDataException() {
        rateViewModel.requestDataExceptionLiveData?.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                if (ExceptionHandler.isNotCancelledRequest(exception)) {
                    ExceptionHandler.exception = exception
                    binding.textViewLoadingFragmentRate.text = ExceptionHandler.message()
                    binding.progressbarLoadingFragmentRate.visibility = GONE
                    binding.buttonRefreshFragmentRate.visibility = VISIBLE
                }
            }
        }
    }

    private fun regulateRatesList() {
        when (baseCurrencyCode) {
            "USD" -> {
                binding.constraintLayoutUsdRateFragmentRate.visibility = GONE
            }
            "IRR" -> {
                binding.constraintLayoutIrrRateFragmentRate.visibility = GONE
            }
            "RUB" -> {
                binding.constraintLayoutRubRateFragmentRate.visibility = GONE
            }
            "CNY" -> {
                binding.constraintLayoutCnyRateFragmentRate.visibility = GONE
            }
        }
    }

    override fun setOnClicks() {
        onRefreshButtonClicked()
        onBackButtonClicked()
    }

    private fun onRefreshButtonClicked() {
        binding.buttonRefreshFragmentRate.setOnClickListener {
            rateViewModel.requestDataExceptionLiveData?.postValue(null)
            requestToGetData()
            binding.buttonRefreshFragmentRate.visibility = GONE
            binding.progressbarLoadingFragmentRate.visibility = VISIBLE
            binding.textViewLoadingFragmentRate.setText(R.string.get_rates_loading)
        }
    }

    private fun onBackButtonClicked() {
        binding.imageButtonBackFragmentRate.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

}