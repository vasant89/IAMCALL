package com.he.iamcall.di

import android.app.Application
import com.he.iamcall.data.source.Repository
import com.he.iamcall.di.module.*
import com.he.iamcall.di.picassomodules.NetworkModule
import com.he.iamcall.di.picassomodules.PicassoModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            (ApplicationModule::class),
            (LocalRepositoryModule::class),
            (AndroidSupportInjectionModule::class),
            (ActivityBindingModule::class),
            (PicassoModule::class),
            (NetworkModule::class),
            (ViewModelModule::class),
            (SingleClassProviderModule::class)
        ]
)
interface AppComponent : AndroidInjector<App> {

    fun getRepository(): Repository

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // Application will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}