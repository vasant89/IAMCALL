package com.he.iamcall.binding

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import com.he.iamcall.adapters.AlphabetAdapter
import com.he.iamcall.adapters.ContactAdapter
import com.he.iamcall.data.Contact

object ListBinding {

    private val TAG = ListBinding::class.java

    @BindingAdapter("alphabets")
    @JvmStatic
    fun setAlphabetsList(recyclerView: RecyclerView, alphabets: List<String>) {
        (recyclerView.adapter as AlphabetAdapter).apply {
            this.replaceAlphabets(alphabets)
        }
    }

    @BindingAdapter("contacts")
    @JvmStatic
    fun setContactsList(recyclerView: RecyclerView, contacts: List<Contact>) {
        (recyclerView.adapter as ContactAdapter).apply {
            this.replaceContacts(contacts)
        }
    }
}