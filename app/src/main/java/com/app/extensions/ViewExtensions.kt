package com.app.extensions

import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher

fun EditText.onQueryTextChange(action: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(newText : CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(editable : Editable) {
            if(editable.isNotEmpty()) action.invoke(editable.toString())
        }
    })
}