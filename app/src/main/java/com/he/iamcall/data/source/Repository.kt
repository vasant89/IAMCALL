package com.he.iamcall.data.source

import android.arch.lifecycle.LiveData
import com.he.iamcall.data.Contact
import com.he.iamcall.di.scope.Local
import com.he.iamcall.di.scope.Remote
import com.he.iamcall.extenstions.launchSilent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository
@Inject
constructor(
        @Remote private val remoteDataSource: DataSource,
        @Local private val localDataSource: DataSource
) : DataSource {

    init {
        launchSilent {
            updateContact(null, null)
        }
    }

    override fun getContacts(): LiveData<List<Contact>> {
        return localDataSource.getContacts()
    }

    override fun getContactsByAlpha(alpha: String): LiveData<List<Contact>> {
        return localDataSource.getContactsByAlpha(alpha)
    }

    override fun getContactsByQuery(query: String?): LiveData<List<Contact>> {
        return localDataSource.getContactsByQuery(query)
    }

    override suspend fun insertContact(contact: Contact) {
        remoteDataSource.insertContact(contact)
    }

    override suspend fun insertContacts(contacts: List<Contact>) {
        localDataSource.insertContacts(contacts)
    }

    override suspend fun deleteContact(contact: Contact) {
        localDataSource.deleteContact(contact)
    }

    override suspend fun updateContact(contact: Contact?, callback: DataSource.ContactAddUpdateCallback?) {
        return remoteDataSource.updateContact(contact, object : DataSource.ContactAddUpdateCallback {
            override fun onContactAdded(contact: Contact) = launchSilent {
                localDataSource.insertContact(contact)
            }

            override fun onContactUpdated(contact: Contact) = launchSilent {
                localDataSource.updateContact(contact, callback)
            }

            override fun onContactDeleted(contact: Contact) = launchSilent {
                localDataSource.deleteContact(contact)
            }
        })
    }

    override suspend fun deleteContacts() {
        localDataSource.deleteContacts()
    }

    override suspend fun refreshContact(callback: DataSource.ContactCallback) {
        remoteDataSource.refreshContact(object : DataSource.ContactCallback {
            override fun onContactsFound(contacts: List<Contact>) = launchSilent {
                contacts.forEach {
                    localDataSource.insertContact(it)
                }
                callback.onContactsFound(contacts)
            }

            override fun onContactsNotFound(exception: RemoteDataNotFoundException) {
                callback.onContactsNotFound(exception)
            }

        })
    }

}