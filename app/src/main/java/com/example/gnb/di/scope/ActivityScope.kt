package com.example.gnb.di.scope

import javax.inject.Scope

/**
 * Annotation class used for annotating other classes in order to establish their scope
 */
@Scope
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope