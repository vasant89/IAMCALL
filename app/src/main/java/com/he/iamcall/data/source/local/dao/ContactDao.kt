package com.he.iamcall.data.source.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.db.SupportSQLiteQuery
import android.arch.persistence.room.*
import com.he.iamcall.data.Contact

@Dao
interface ContactDao {

    @Query("SELECT * FROM Contact")
    fun getContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM Contact WHERE alpha = :alpha")
    fun getContactsByAlpha(alpha: String): LiveData<List<Contact>>

    @Query("SELECT * FROM Contact WHERE first LIKE '%' || :query || '%' OR middle LIKE '%' || :query || '%' OR last LIKE '%' || :query || '%' OR mobile LIKE '%' || :query || '%'")
    fun getContactsByQuery(query: String?): LiveData<List<Contact>>

    @RawQuery(observedEntities = [Contact::class])
    fun getContactsByQuery(query: SupportSQLiteQuery): LiveData<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContacts(contacts: List<Contact>)

    @Update
    fun updateContact(contact: Contact): Int

    @Delete
    fun deleteContact(contact: Contact)

    @Query("DELETE FROM Contact")
    fun deleteContacts()
}