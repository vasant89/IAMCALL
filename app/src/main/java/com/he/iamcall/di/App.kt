package com.he.iamcall.di

import android.content.Context
import android.support.multidex.MultiDex
import com.google.android.gms.ads.MobileAds
import com.he.iamcall.R
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication


/**
 * Created by DOTh Solutions on 20-01-2018.
 */
class App : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this, getString(R.string.admob_app_id))
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}