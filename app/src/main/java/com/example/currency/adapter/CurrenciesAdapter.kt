package com.example.currency.adapter

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.currency.R
import com.example.currency.databinding.ItemCurrencyFragmentHomeBinding
import com.example.currency.model.Currency
import com.example.currency.viewmodel.HomeViewModel
import java.util.*
import java.util.logging.Handler
import kotlin.concurrent.timerTask

class CurrenciesAdapter(private val currencyList: List<Currency>) :
    RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCurrencyFragmentHomeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_currency_fragment_home,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencyList[position]
        val homeViewModel = HomeViewModel()

        holder.binding.currency = currency
        holder.binding.buttonShowRateItemCurrency.setOnClickListener {
            android.os.Handler(Looper.getMainLooper()).postDelayed({
                homeViewModel.navigateToCurrencyRateFragment(view = it, baseCode = currency.code)
            }, 150)
        }
    }

    override fun getItemCount(): Int = currencyList.size
}