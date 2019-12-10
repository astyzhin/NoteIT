package com.akmisoftware.noteit.di.module

import android.app.Application
import android.content.Context
import com.akmisoftware.noteit.di.builder.ViewModelBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelBuilder::class])
class ContextModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}