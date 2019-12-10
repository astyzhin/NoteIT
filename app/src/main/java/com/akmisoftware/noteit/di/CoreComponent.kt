package com.akmisoftware.noteit.di

import android.app.Application
import com.akmisoftware.noteit.App
import com.akmisoftware.noteit.di.builder.ActivityBuilder
import com.akmisoftware.noteit.di.module.CompositeDisposableModule
import com.akmisoftware.noteit.di.module.ContextModule
import com.akmisoftware.noteit.di.module.DatabaseModule
import com.akmisoftware.noteit.di.module.ListenerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, ActivityBuilder::class,
        DatabaseModule::class, ContextModule::class, CompositeDisposableModule::class,
        ListenerModule::class]
)
interface CoreComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent

        @BindsInstance
        fun listenerModule(listenerModule: ListenerModule): Builder
    }
}