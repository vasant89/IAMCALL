package com.he.iamcall.main

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import com.he.iamcall.SingleLiveEvent
import com.he.iamcall.data.Contact
import com.he.iamcall.data.source.DataSource
import com.he.iamcall.data.source.RemoteDataNotFoundException
import com.he.iamcall.data.source.Repository
import com.he.iamcall.session.SessionManager
import com.he.iamcall.utils.ImageHelper
import com.he.iamcall.extenstions.launchSilent
import kotlin.coroutines.CoroutineContext


class MainViewModel(
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

    private fun showSnackbarMessage(message: String) = launchSilent(uiContext) {
        snackbarMessage.value = message
    }

    val alphabets = ObservableArrayList<String>()
    val contacts = ObservableArrayList<Contact>()
    val searchResultEvent = SingleLiveEvent<List<Contact>>()


    fun start() = launchSilent(uiContext) {

    }

    fun showSearchResult(query: String) {
        searchResultEvent.value = if (query.isNotEmpty()) {
            (contacts.filter {
                (it.first
                        + it.middle
                        + it.last
                        + it.mobile).replace("\\s".toRegex(), "")
                        .toLowerCase()
                        .contains(query.replace("\\s".toRegex(), "").toLowerCase())
            })
        } else {
            emptyList()
        }
    }


    fun refreshContact() = launchSilent(uiContext) {

        isDataLoading.value = true

        repository.refreshContact(object : DataSource.ContactCallback {
            override fun onContactsFound(contacts: List<Contact>) = launchSilent(uiContext) {
                isDataLoading.value = false
            }

            override fun onContactsNotFound(exception: RemoteDataNotFoundException) = launchSilent(uiContext) {
                isDataLoading.value = false
                showSnackbarMessage(exception.message.toString())
            }
        })
    }


    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }
}