package com.he.iamcall.di.module

import android.content.Context
import com.he.iamcall.di.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by DOTh Solutions on 20-01-2018.
 */
@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideContext(): Context {
        return app.applicationContext
    }

}