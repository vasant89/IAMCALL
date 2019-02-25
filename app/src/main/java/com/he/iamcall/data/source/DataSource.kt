package com.he.iamcall.data.source

import android.arch.lifecycle.LiveData
import com.he.iamcall.data.Contact

interface DataSource {

    fun getContacts(): LiveData<List<Contact>>

    fun getContactsByAlpha(alpha: String): LiveData<List<Contact>>

    fun getContactsByQuery(query: String?): LiveData<List<Contact>>

    suspend fun insertContact(contact: Contact)

    suspend fun insertContacts(contacts: List<Contact>)

    suspend fun updateContact(contact: Contact?, callback: ContactAddUpdateCallback?)

    suspend fun deleteContact(contact: Contact)

    suspend fun deleteContacts()

    interface ContactAddUpdateCallback {
        fun onContactAdded(contact: Contact)
        fun onContactUpdated(contact: Contact)
        fun onContactDeleted(contact: Contact)
    }

    interface ContactCallback {
        fun onContactsFound(contacts: List<Contact>)
        fun onContactsNotFound(exception: RemoteDataNotFoundException)
    }

    suspend fun refreshContact(callback: ContactCallback)
}