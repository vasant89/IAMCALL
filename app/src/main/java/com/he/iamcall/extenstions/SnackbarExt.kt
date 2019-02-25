package com.he.iamcall.extenstions

import android.support.design.widget.Snackbar
import android.view.View

fun View.showSnackBarShort(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.showSnackBarLong(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}