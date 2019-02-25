package com.he.iamcall

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.google.android.gms.common.api.GoogleApiClient
import com.he.iamcall.data.source.Repository
import com.he.iamcall.main.MainViewModel
import com.he.iamcall.main.contactlist.ContactListViewModel
import com.he.iamcall.session.SessionManager

import com.he.iamcall.utils.ImageHelper
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


class ViewModelFactory(
        private val application: Application,
        private val repository: Repository,
        private val sMg: SessionManager,
        private val picasso: Picasso,
        private val imageHelper: ImageHelper,
        private val uiContext: CoroutineContext = Dispatchers.Main
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(MainViewModel::class.java) ->
                        MainViewModel(application, repository, sMg, imageHelper, uiContext)
                    isAssignableFrom(ContactListViewModel::class.java) ->
                        ContactListViewModel(application, repository, sMg, imageHelper, uiContext)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
}