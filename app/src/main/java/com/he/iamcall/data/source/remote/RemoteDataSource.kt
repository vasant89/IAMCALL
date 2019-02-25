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

    override suspend fun insertContact(contact: Contact) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun insertContacts(contacts: List<Contact>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteContact(contact: Contact) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun updateContact(contact: Contact?, callback: DataSource.ContactAddUpdateCallback?) {
        fireStoreDb
                .collection("PhoneBook")
                .document("Contact")
                .collection("contacts")
                .addSnapshotListener { snapshot, firebaseFirestoreException ->
                    if (snapshot != null) {
                        for (documentChange in snapshot.documentChanges) {
                            when (documentChange.type) {
                                DocumentChange.Type.ADDED -> {
                                    callback?.onContactAdded(documentChange.document.toObject(Contact::class.java))
                                }
                                DocumentChange.Type.MODIFIED -> {
                                    callback?.onContactUpdated(documentChange.document.toObject(Contact::class.java))
                                }
                                DocumentChange.Type.REMOVED -> {
                                    callback?.onContactDeleted(documentChange.document.toObject(Contact::class.java))
                                }
                            }
                        }
                    }else{
                        Log.e(TAG,"Log Error Firebase Firestore Exception ${firebaseFirestoreException?.message}")
                    }
                }
    }

    override suspend fun deleteContacts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun refreshContact(callback: DataSource.ContactCallback) {
        fireStoreDb
                .collection("PhoneBook")
                .document("Contact")
                .collection("contacts")
                .get()
                .addOnSuccessListener {
                    if (!it.isEmpty) {
                        callback.onContactsFound(it.toObjects(Contact::class.java))
                    } else {
                        callback.onContactsNotFound(RemoteDataNotFoundException())

                    }
                }.addOnFailureListener {
                    callback.onContactsNotFound(RemoteDataNotFoundException())
                }
    }
}