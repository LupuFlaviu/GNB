package com.example.gnb.di.module

import com.example.gnb.ui.main.fragment.MainFragment
import com.example.gnb.ui.main.fragment.ProductFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module specifying which fragments should be injected
 */

@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun provideMainFragmentInjector(): MainFragment

    @ContributesAndroidInjector
    internal abstract fun provideProductFragmentInjector(): ProductFragment
}