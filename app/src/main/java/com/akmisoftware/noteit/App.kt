package com.akmisoftware.noteit

import com.akmisoftware.noteit.di.DaggerCoreComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerCoreComponent
            .builder()
            .application(this)
//            .listenerModule(ListenerModule())
            .build()
    }
}