package com.example.currency.util

import android.widget.Toast

class ExceptionHandler {

    companion object {
        var exception: Exception? = null

        fun isNotCancelledRequest(exception: Exception): Boolean {
            return exception.message.toString() != "Job was cancelled"
                    && exception.message.toString() != "StandaloneCoroutine was cancelled"
        }

        fun message(): String {
            return if (exception?.message.toString() == "Read timed out") {
                "ارتباط با سرور برقرار نشد! لطفا اتصال خود به vpn را بررسی کنید"
            } else if (exception?.message.toString() == "timeout") {
                "نتیجه ای از سرور دریافت نشد. لطفا مجدد امتحان کنید"
            } else if (exception?.message.toString().contains("Unable to resolve host")) {
                "ارتباط با سرور برقرار نشد! لطفا اتصال خود به اینترنت را بررسی کنید"
            } else {
                "ارتباط با سرور برقرار نشد! لطفا دوباره امتحان کنید"
            }
        }
    }
}