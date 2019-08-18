package com.he.iamcall.di.module

import android.content.Context

import com.google.android.gms.common.api.GoogleApiClient
import com.securepreferences.SecurePreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module // what ever dependency you need form other module add here
class SingleClassProviderModule {

    @Singleton
    @Provides
    fun provideSecurePreferences(context: Context): SecurePreferences {
        return SecurePreferences(context)
    }

}
