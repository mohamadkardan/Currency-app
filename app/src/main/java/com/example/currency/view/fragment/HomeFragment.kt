package com.example.currency.view.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.currency.R
import com.example.currency.adapter.CurrenciesAdapter
import com.example.currency.databinding.FragmentHomeBinding
import com.example.currency.model.Currency
import com.example.currency.util.CustomDialog
import com.example.currency.util.ExceptionHandler
import com.example.currency.viewmodel.HomeViewModel
import com.google.android.material.animation.AnimationUtils

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private var viewModel: HomeViewModel? = null
    var fromSpinnerPosition: Int? = null
    var toSpinnerPosition: Int? = null
    lateinit var baseCode: String
    lateinit var targetCode: String
    lateinit var amount: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        initViewModel()
        setCurrenciesRecyclerview()
        setCurrencySpinners()
        observeConvertResult()
        onChangeConvertProgressbarStatus()
        observeConvertResultException()
        setOnClicks()
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    private fun setCurrenciesRecyclerview() {
        val currenciesAdapter = CurrenciesAdapter(viewModel!!.getCurrencyList().toList())
        binding.recyclerviewCurrencyFragmentHome.apply {
            layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            adapter = currenciesAdapter
        }
    }

    override fun setOnClicks() {
        onConvertButtonClicked()
        onDisplacementImageButtonClicked()
        onConvertCancelButtonClicked()
    }

    private fun setCurrencySpinners() {
        binding.spinnerFromCurrencyFragmentHome.adapter =
            viewModel?.getSpinnerAdapter(requireContext())
        binding.spinnerToCurrencyFragmentHome.adapter =
            viewModel?.getSpinnerAdapter(requireContext())
        binding.spinnerToCurrencyFragmentHome.setSelection(1)
    }

    private fun onChangeConvertProgressbarStatus() {
        viewModel?.isGettingData?.observe(viewLifecycleOwner) { isGettingData ->
            if (isGettingData) {
                binding.buttonConvertFragmentHome.visibility = GONE
                binding.progressbarLoadingFragmentHome.visibility = VISIBLE
                binding.buttonCancelFragmentHome.visibility = VISIBLE
            } else {
                binding.buttonConvertFragmentHome.visibility = VISIBLE
                binding.progressbarLoadingFragmentHome.visibility = GONE
                binding.buttonCancelFragmentHome.visibility = GONE
            }
        }
    }

    private fun onConvertButtonClicked() {
        binding.buttonConvertFragmentHome.setOnClickListener {
            setCurrencyConvertRequestData()
            if (amount.isNotBlank()) {
                if (amount.toInt() < 1) {
                    Toast.makeText(requireContext(), "مقدار 0 قابل محاسبه نیست", LENGTH_SHORT)
                        .show()
                } else {
                    viewModel?.getConvertResult(
                        baseCode = baseCode,
                        targetCode = targetCode,
                        amount = amount
                    )
                    it.hideKeyboard()
                }
            } else {
                Toast.makeText(requireContext(), "لطفا مقدار ارز مبدا را وارد نمایید", LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun onConvertCancelButtonClicked() {
        binding.buttonCancelFragmentHome.setOnClickListener {
            viewModel?.cancelConvertResultRequest()
        }
    }

    private fun observeConvertResult() {
        viewModel?.convertorResultLiveData?.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it != "") {
                    viewModel?.isGettingData?.postValue(false)
                    val message = "$amount $baseCode = ${it.toFloat()} $targetCode"
                    binding.editTextAmountFragmentHome.setText("")
                    CustomDialog.showDialog(
                        activity = requireActivity(),
                        message = message
                    )
                }
            }
        }
    }

    private fun observeConvertResultException() {
        viewModel?.convertResultExceptionLiveData?.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                if (ExceptionHandler.isNotCancelledRequest(exception)) {
                    ExceptionHandler.exception = exception
                    Toast.makeText(requireContext(), ExceptionHandler.message(), LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun onDisplacementImageButtonClicked() {
        binding.imageButtonReplacementFragmentHome.setOnClickListener {
            toSpinnerPosition = binding.spinnerToCurrencyFragmentHome.selectedItemPosition
            fromSpinnerPosition = binding.spinnerFromCurrencyFragmentHome.selectedItemPosition
            binding.spinnerToCurrencyFragmentHome.setSelection(fromSpinnerPosition!!)
            binding.spinnerFromCurrencyFragmentHome.setSelection(toSpinnerPosition!!)
        }
    }

    private fun setCurrencyConvertRequestData() {
        amount = binding.editTextAmountFragmentHome.text.toString()
        baseCode = binding.spinnerFromCurrencyFragmentHome.selectedItem.toString()
        targetCode = binding.spinnerToCurrencyFragmentHome.selectedItem.toString()
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel?.convertorResultLiveData?.postValue(null)
        viewModel?.convertResultExceptionLiveData?.postValue(null)
    }

    override fun onPause() {
        super.onPause()
        viewModel?.cancelConvertResultRequest()
        binding.editTextAmountFragmentHome.setText("")
    }

}