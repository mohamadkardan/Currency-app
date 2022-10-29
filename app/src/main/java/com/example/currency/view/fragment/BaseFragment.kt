package com.example.currency.view.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment(val contentLayoutId: Int) : Fragment(contentLayoutId) {

    abstract fun initViewModel()

    abstract fun setOnClicks()

}