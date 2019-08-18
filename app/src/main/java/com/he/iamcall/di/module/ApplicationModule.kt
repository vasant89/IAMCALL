package com.he.iamcall.di.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * Created by DOTh Solutions on 20-01-2018.
 */
@Module
abstract class ApplicationModule {
    //expose Application as an injectable context
    @Binds
    internal abstract fun bindContext(application: Application): Context

}