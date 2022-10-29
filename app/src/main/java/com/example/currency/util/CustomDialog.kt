package com.example.currency.util

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.currency.R


class CustomDialog {

    companion object {

        private lateinit var dialog: Dialog

        fun showDialog(activity: Activity, message: String?) {
            initDialog(activity)
            initDialogViews(message)
            dialog.show()
        }

        private fun initDialog(activity: Activity) {
            dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.result_convert_custom_dialog)
        }

        private fun initDialogViews(message: String?) {
            val textViewResult =
                dialog.findViewById<View>(R.id.textView_result_customDialog) as TextView
            textViewResult.text = message

            val dialogButton: AppCompatButton =
                dialog.findViewById<View>(R.id.button_done_customDialog) as AppCompatButton

            dialogButton.setOnClickListener { dialog.dismiss() }
        }

    }

}