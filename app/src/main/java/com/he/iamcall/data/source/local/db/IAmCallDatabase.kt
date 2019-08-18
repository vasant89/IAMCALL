package com.he.iamcall.data.source.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.he.iamcall.data.Contact
import com.he.iamcall.data.source.local.dao.ContactDao

@Database(entities = [Contact::class],version = 1)
abstract class IAmCallDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}