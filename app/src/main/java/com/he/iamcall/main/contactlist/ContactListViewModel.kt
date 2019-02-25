package com.he.iamcall.main.contactlist

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.he.iamcall.SingleLiveEvent
import com.he.iamcall.data.source.Repository
import com.he.iamcall.session.SessionManager
import com.he.iamcall.utils.ImageHelper
import kotlin.coroutines.CoroutineContext

class ContactListViewModel(
        context: Application,
        val repository: Repository,
        private val sMg: SessionManager,
        val imageHelper: ImageHelper,
        private val uiContext: CoroutineContext
) : AndroidViewModel(context) {

    @SuppressLint("StaticFieldLeak")
    private val ctx: Context = context.applicationContext //Application Context to avoid leaks.

    val isDataLoading = SingleLiveEvent<Boolean>()
    val isDataLoadingError = ObservableBoolean(false)

    val empty = ObservableBoolean(false)

    val snackbarMessage = SingleLiveEvent<String>()

    private fun showSnackbarMessage(message: String) {
        snackbarMessage.value = message
    }


    val alphabet = ObservableField<String>("")

    fun start(alphabet: String) {
        this.alphabet.set(alphabet)
    }


    companion object {
        private val TAG = ContactListView::class.java.simpleName
    }
}