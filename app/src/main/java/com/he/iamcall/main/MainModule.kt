package com.he.iamcall.main

import com.he.iamcall.di.scope.ActivityScoped
import com.he.iamcall.di.scope.FragmentScoped
import com.kaopiz.kprogresshud.KProgressHUD
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun mainView(): MainView

    @Module
    companion object {

        @JvmStatic
        @Provides
        @ActivityScoped
        fun provideProgressDialog(activity: MainActivity): KProgressHUD {
            return KProgressHUD.create(activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(1)
                    .setDimAmount(0.5f)

        }
    }
}