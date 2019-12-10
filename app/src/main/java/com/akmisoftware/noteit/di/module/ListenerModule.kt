package com.akmisoftware.noteit.di.module

import com.akmisoftware.noteit.ui.MainActivity
import com.akmisoftware.noteit.ui.interaction.ListenerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ListenerModule {

    @Provides
    @Singleton
    fun provideMainActivity(): MainActivity {
        return MainActivity()
    }

    @Provides
    @Singleton
    fun provideInteractionListeners(activity: MainActivity): ListenerImpl {
        return ListenerImpl(activity)
    }
}