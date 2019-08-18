package com.he.iamcall.di.module

import android.app.Application
import android.content.Context


import com.google.android.gms.common.api.GoogleApiClient
import com.he.iamcall.ViewModelFactory
import com.he.iamcall.data.source.Repository
import com.he.iamcall.session.SessionManager
import com.he.iamcall.utils.ImageHelper
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by DOTh Solutions on 23-02-2018.
 */
@Module
class ViewModelModule {

    @Singleton
    @Provides
    fun providePicassoGetImageHelper(picasso: Picasso, context: Context): ImageHelper {
        return ImageHelper(picasso, context)
    }

    @Singleton
    @Provides
    fun provideViewModelFactory(context: Application, repository: Repository, sMg: SessionManager, picasso: Picasso, imageHelper: ImageHelper): ViewModelFactory {
        return ViewModelFactory(context, repository, sMg,  picasso, imageHelper)
    }
}