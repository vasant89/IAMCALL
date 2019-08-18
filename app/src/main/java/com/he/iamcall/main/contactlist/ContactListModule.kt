package com.he.iamcall.main.contactlist

import com.he.iamcall.di.scope.ActivityScoped
import com.he.iamcall.di.scope.FragmentScoped
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ContactListModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contactListView(): ContactListView

    @Module
    companion object {

        @JvmStatic
        @Provides
        @ActivityScoped
        fun provideProgressDialog(activity: ContactListActivity): KProgressHUD {
            return KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)
        }
    }
}