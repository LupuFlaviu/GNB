package com.example.gnb.di.module

import androidx.lifecycle.ViewModel
import com.example.gnb.MainActivity
import com.example.gnb.di.scope.ActivityScope
import com.example.gnb.di.viewmodel.ViewModelKey
import com.example.gnb.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module specifying all activities that should be injected together with their corresponding viewmodels
 */

@Module
internal abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun provideMainActivityInjector(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainViewModel): ViewModel
}