package com.he.iamcall.binding

import android.databinding.BindingAdapter
import android.widget.TextView

@BindingAdapter("text")
fun setText(textView: TextView, text: String?) {
    text?.let {
        textView.text = text
    }
}