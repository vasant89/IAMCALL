package com.he.iamcall.di.module


import com.he.iamcall.SplashActivity
import com.he.iamcall.main.MainActivity
import com.he.iamcall.di.scope.ActivityScoped
import com.he.iamcall.main.MainModule
import com.he.iamcall.main.contactlist.ContactListActivity
import com.he.iamcall.main.contactlist.ContactListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ContactListModule::class])
    internal abstract fun contactListActivity(): ContactListActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun splashActivity(): SplashActivity
}