package com.example.gnb.di

import android.app.Application
import com.example.gnb.AndroidApplication
import com.example.gnb.di.module.ActivityModule
import com.example.gnb.di.module.ApiModule
import com.example.gnb.di.module.ApplicationModule
import com.example.gnb.di.module.FragmentModule
import com.example.gnb.di.viewmodel.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class, AndroidSupportInjectionModule::class,
        ApplicationModule::class, ViewModelModule::class, ActivityModule::class, FragmentModule::class,
        ApiModule::class]
)

/**
 * Application component that specify which modules should be injected
 */
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    fun inject(application: AndroidApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
