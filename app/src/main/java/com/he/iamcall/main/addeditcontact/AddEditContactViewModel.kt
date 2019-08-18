package com.he.iamcall.main.addeditcontact

import android.annotation.SuppressLint
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import com.he.iamcall.SingleLiveEvent
import com.he.iamcall.data.Contact
import com.he.iamcall.data.source.DataSource
import com.he.iamcall.data.source.Repository
import com.he.iamcall.extenstions.launchSilent
import com.he.iamcall.session.SessionManager
import com.he.iamcall.utils.ImageHelper
import kotlin.coroutines.CoroutineContext

class AddEditContactViewModel(context: Application,
                              val repository: Repository,
                              private val sMg: SessionManager,
                              val imageHelper: ImageHelper,
                              private val uiContext: CoroutineContext
) : AndroidViewModel(context) {
    @SuppressLint("StaticFieldLeak")
    private val ctx: Context = context.applicationContext //Application Context to avoid leaks.

    val contact = ObservableField<Contact>(Contact())

    val isDataLoading = SingleLiveEvent<Boolean>()
    val isDataLoadingError = ObservableBoolean(false)

    val empty = ObservableBoolean(false)

    val snackbarMessage = SingleLiveEvent<String>()

    val addUpdateContactEvent = SingleLiveEvent<String>()

    private fun showIsDataLoading(isLoading:Boolean)= launchSilent(uiContext) {
        isDataLoading.value = isLoading
    }
    private fun showSnackbarMessage(message: String) = launchSilent(uiContext) {
        snackbarMessage.value = message
    }

    private fun showAddUpdateContactEvent(message:String)= launchSilent(uiContext){
        addUpdateContactEvent.value = message
    }
    fun start(contact: Contact?) {
        contact?.let {
            this.contact.set(it)
        }
    }

    fun addUpdateContact() = launchSilent(uiContext){
        val contact = contact.get() ?:return@launchSilent
        when{
            contact.name.isNullOrEmpty()->{
                showSnackbarMessage("Please Enter Full Name In Gujarati.")
            }
            contact.first.isNullOrEmpty()->{
                showSnackbarMessage("Please Enter First Name.")
            }
            contact.middle.isNullOrEmpty()->{
                showSnackbarMessage("Please Enter Middle Name.")
            }
            contact.last.isNullOrEmpty()->{
                showSnackbarMessage("Please Enter Last Name.")
            }
            contact.mobile.isNullOrEmpty()->{
                showSnackbarMessage("Please Enter Mobile Number.")
            }
            else->{
                contact.let {
                    showIsDataLoading(true)
                    it.alpha = it.first!![0].toString().toUpperCase()
                    if(it.fId.isNullOrEmpty()){
                        repository.insertContact(it,object :DataSource.AddContactCallback{
                            override fun onAddContactSuccessfully() {
                                showIsDataLoading(false)
                                showAddUpdateContactEvent("Contact Added Successfully.")
                            }

                            override fun onAddContactError() {
                                showIsDataLoading(false)
                                showSnackbarMessage("Contact Not Added!!!")
                            }

                        })
                    }else{
                        repository.updateContact(it, object : DataSource.ContactAddUpdateCallback{
                            override fun onContactAdded(contact: Contact) {
                                showIsDataLoading(false)
                            }

                            override fun onContactUpdated(contact: Contact) {
                                showIsDataLoading(false)
                                showAddUpdateContactEvent("Contact Updated Successfully.")
                            }

                            override fun onContactDeleted(contact: Contact) {
                                showIsDataLoading(false)
                            }

                            override fun onContactUpdatedError() {
                                showIsDataLoading(false)
                                showSnackbarMessage("Contact Not Updated!!!")
                            }
                        })
                    }
                }
            }
        }
    }

    fun deleteContact() = launchSilent(uiContext){
        val contact = contact.get() ?:return@launchSilent
        if(!contact.fId.isNullOrEmpty()){
            showIsDataLoading(true)
            repository.deleteContact(contact,object :DataSource.DeleteContactCallback{
                override fun onDeleteContactSuccessfully() {
                    showIsDataLoading(false)
                    showAddUpdateContactEvent("Contact Deleted Successfully!!!")
                }

                override fun onDeleteContactError() {
                    showIsDataLoading(false)
                    showSnackbarMessage("Contact Not Deleted!!!")
                }

            })
        }else{
            showSnackbarMessage("Contact Id Id Not Found!!!")
        }
    }

    companion object {
        private val TAG = AddEditContactViewModel::class.java.simpleName
    }
}