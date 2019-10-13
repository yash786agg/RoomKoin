package com.app.helper

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class UiHelper(private val context : Context) {

    fun toast(content: String) = Toast.makeText(context, content, Toast.LENGTH_LONG).show()

    fun showSnackBar(view: View, content: String) = Snackbar.make(view, content, Snackbar.LENGTH_LONG).show()
}