package com.app.ui.birds

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.helper.UiHelper
import com.app.roomkoin.R

@BindingAdapter(value = ["timeStamp"])
fun timeStamp(textView : TextView, timeStamp : Long?) {

    timeStamp?.let {

        val uiHelper = UiHelper(textView.context)
        uiHelper.convertTimeStampToDate(timeStamp).let {
            val value = textView.context.resources.getString(R.string.dateTime)+" : "+ it
            textView.text = value
        }

    }
}