package com.he.iamcall.data.source.local

import android.arch.lifecycle.LiveData
import com.he.iamcall.data.Contact
import com.he.iamcall.data.source.DataSource
import com.he.iamcall.data.source.local.dao.ContactDao
import com.he.iamcall.extenstions.AppExecutors
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource
@Inject
constructor(
        private val appExecutors: AppExecutors,
        private val contactDao: ContactDao
) : DataSource {

    override fun getContacts(): LiveData<List<Contact>> = contactDao.getContacts()

    override fun getContactsByAlpha(alpha: String): LiveData<List<Contact>> {
        return contactDao.getContactsByAlpha(alpha)
    }

    override fun getContactsByQuery(query: String?): LiveData<List<Contact>> {
        return contactDao.getContactsByQuery(query)
    }

    override suspend fun insertContact(contact: Contact) {
        withContext(appExecutors.ioContext) {
            contactDao.insertContact(contact)
        }
    }

    override suspend fun insertContacts(contacts: List<Contact>) {
        withContext(appExecutors.ioContext) {
            contactDao.insertContacts(contacts)
        }
    }

    override suspend fun updateContact(contact: Contact?, callback: DataSource.ContactAddUpdateCallback?) {
        withContext(appExecutors.ioContext) {
            contact?.let {
                contactDao.updateContact(it)
            }
        }
    }

    override suspend fun deleteContact(contact: Contact) {
        withContext(appExecutors.ioContext) {
            contactDao.deleteContact(contact)
        }
    }

    override suspend fun deleteContacts() {
        withContext(appExecutors.ioContext) {
            contactDao.deleteContacts()
        }
    }

    override suspend fun refreshContact(callback: DataSource.ContactCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}