package com.example.currency.model

import androidx.databinding.BindingAdapter
import de.hdodenhof.circleimageview.CircleImageView

data class Currency(var fullName: String, var code: String, var countryFlag: Int) {

    companion object {
        @JvmStatic
        @BindingAdapter("android:setImage")
        fun setImage(circleImageView: CircleImageView, imageId: Int) {
            circleImageView.setImageResource(imageId)
        }
    }

}
