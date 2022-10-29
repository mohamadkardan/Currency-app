package com.example.currency.model

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.currency.R
import com.google.gson.annotations.SerializedName
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DecimalFormat

data class RatesByBaseCodeResponse(
    @SerializedName("result") val result: String,
    @SerializedName("base_code") val baseCode: String,
    @SerializedName("conversion_rates") val conversionRates: ConversionRates
) {
    companion object {
        @JvmStatic
        @BindingAdapter("setImageByBaseCode")
        fun setFlagByBaseCode(circleImageView: CircleImageView, baseCode: String) {
            when (baseCode) {
                "IRR" -> {
                    circleImageView.setImageResource(R.drawable.ic_flag_iran)
                }
                "USD" -> {
                    circleImageView.setImageResource(R.drawable.ic_flag_united_states)
                }
                "RUB" -> {
                    circleImageView.setImageResource(R.drawable.ic_flag_russia)
                }
                "CNY" -> {
                    circleImageView.setImageResource(R.drawable.ic_flag_china)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("setTextByBaseCode")
        fun setCurrencyNameByBaseCode(textView: TextView, baseCode: String) {
            when (baseCode) {
                "IRR" -> {
                    textView.setText(R.string.rial)
                }
                "USD" -> {
                    textView.setText(R.string.dollar)
                }
                "RUB" -> {
                    textView.setText(R.string.rubl)
                }
                "CNY" -> {
                    textView.setText(R.string.yuan)
                }
            }
        }

        @JvmStatic
        @BindingAdapter("setCurrencyRate")
        fun setTextByCurrencyRate(textView: TextView, rate: Double) {
            val decimalFormat = DecimalFormat("0.00000")
            if (rate == 1.00000) {
                textView.text = "1.0"
            } else
                textView.text = decimalFormat.format(rate)
        }
    }
}