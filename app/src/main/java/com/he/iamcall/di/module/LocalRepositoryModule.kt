package com.he.iamcall.di.module

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import android.app.Application
import com.he.iamcall.data.source.DataSource
import com.he.iamcall.data.source.local.LocalDataSource
import com.he.iamcall.data.source.local.db.IAmCallDatabase
import com.he.iamcall.data.source.local.dao.ContactDao
import com.he.iamcall.data.source.remote.RemoteDataSource
import com.he.iamcall.di.scope.Local
import com.he.iamcall.di.scope.Remote
import com.he.iamcall.retrofit.RetrofitServiceFactory
import com.he.iamcall.extenstions.AppExecutors


/**
 * Created by Vasant Vamja on 30-01-2018.
 */

@Module
class LocalRepositoryModule {

    private val THREAD_COUNT = 3

    @Singleton
    @Provides
    @Local
    fun provideTasksLocalDataSource(executors: AppExecutors, contactDao: ContactDao): DataSource {
        return LocalDataSource(executors, contactDao)
    }

    @Singleton
    @Provides
    @Remote
    fun provideTasksRemoteDataSource(context: Application): DataSource {
        return RemoteDataSource(RetrofitServiceFactory(context.applicationContext))
    }

    @Singleton
    @Provides
    fun provideDb(context: Application): IAmCallDatabase {
        return Room.databaseBuilder(context.applicationContext, IAmCallDatabase::class.java, "IAmCallDatabase.db")
                .build()
    }

    @Singleton
    @Provides
    fun provideTblTimeDao(db: IAmCallDatabase): ContactDao {
        return db.contactDao()
    }


    @Singleton
    @Provides
    fun provideAppExecutors(): AppExecutors {
        return AppExecutors()
    }

}