package com.example.gnb.di.module

import android.app.Application
import android.content.Context
import com.example.gnb.AndroidApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module that specifies the application components needed to be injected
 */
@Module
class ApplicationModule {

    /**
     * Provides the application context
     * @param application the [Application] object
     * @return a [Context] object
     */
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application

    /**
     * Provides the [Application] object
     * @param application the Application object
     * @return an [AndroidApplication] object
     */
    @Provides
    @Singleton
    internal fun provideApplication(application: Application): AndroidApplication =
        application as AndroidApplication
}
