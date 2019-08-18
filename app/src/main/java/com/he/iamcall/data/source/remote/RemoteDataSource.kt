package com.he.iamcall.data.source.remote

import android.arch.lifecycle.LiveData
import android.provider.Settings
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.he.iamcall.data.Contact
import com.he.iamcall.data.source.DataSource
import com.he.iamcall.data.source.LocalDataNotFoundException
import com.he.iamcall.data.source.RemoteDataNotFoundException
import com.he.iamcall.data.source.Result
import com.he.iamcall.main.MainViewModel
import com.he.iamcall.retrofit.RetrofitServiceFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource
@Inject
constructor(private val retrofitServiceFactory: RetrofitServiceFactory) : DataSource {


    private val TAG = RemoteDataSource::class.java.simpleName

    val userService = retrofitServiceFactory.createUserService()
    val fireStoreDb = FirebaseFirestore.getInstance()


    override fun getContacts(): LiveData<List<Contact>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContactsByAlpha(alpha: String): LiveData<List<Contact>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContactsByQuery(query: String?): LiveData<List<Contact>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertContact(contact: Contact, callback: DataSource.AddContactCallback?) {
        fireStoreDb
                .collection("PhoneBook")
                .document("Contact")
                .collection("contacts")
                .add(contact)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback?.onAddContactSuccessfully()
                    } else {
                        callback?.onAddContactError()
                    }

                }
                .addOnFailureListener {
                    val message = it.message
                    Log.e(TAG, message)
                    callback?.onAddContactError()
                }
    }

    override suspend fun insertContacts(contacts: List<Contact>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override suspend fun updateContact(contact: Contact?, callback: DataSource.ContactAddUpdateCallback?) {
        if (contact != null) {
            fireStoreDb
                    .collection("PhoneBook")
                    .document("Contact")
                    .collection("contacts")
                    .document(contact.fId!!)
                    .set(contact)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            callback?.onContactUpdated(contact)
                        } else {
                            callback?.onContactUpdatedError()
                        }

                    }
                    .addOnFailureListener {
                        val message = it.message
                        Log.e(TAG, message)
                        callback?.onContactUpdatedError()
                    }
        } else {
            fireStoreDb
                    .collection("PhoneBook")
                    .document("Contact")
                    .collection("contacts")
                    .addSnapshotListener { snapshot, firebaseFirestoreException ->
                        if (snapshot != null) {
                            for (documentChange in snapshot.documentChanges) {
                                val contact: Contact = documentChange.document.toObject(Contact::class.java)
                                contact.fId = documentChange.document.id
                                when (documentChange.type) {
                                    DocumentChange.Type.ADDED -> {
                                        callback?.onContactAdded(contact)
                                    }
                                    DocumentChange.Type.MODIFIED -> {
                                        callback?.onContactUpdated(contact)
                                    }
                                    DocumentChange.Type.REMOVED -> {
                                        callback?.onContactDeleted(contact)
                                    }
                                }
                            }
                        } else {
                            callback?.onContactUpdatedError()
                            Log.e(TAG, "Log Error Firebase Firestore Exception ${firebaseFirestoreException?.message}")
                        }
                    }

        }
    }

    override suspend fun deleteContact(contact: Contact, callback: DataSource.DeleteContactCallback?) {
        fireStoreDb
                .collection("PhoneBook")
                .document("Contact")
                .collection("contacts")
                .document(contact.fId!!)
                .delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback?.onDeleteContactSuccessfully()
                    } else {
                        callback?.onDeleteContactError()
                    }

                }
                .addOnFailureListener {
                    val message = it.message
                    Log.e(TAG, message)
                    callback?.onDeleteContactError()
                }

    }
    override suspend fun deleteContacts() {

    }

}