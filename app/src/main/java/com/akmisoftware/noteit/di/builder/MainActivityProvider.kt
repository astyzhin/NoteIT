package com.akmisoftware.noteit.di.builder

import com.akmisoftware.noteit.ui.AddNoteFragment
import com.akmisoftware.noteit.ui.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityProvider {
    @ContributesAndroidInjector
    abstract fun provideWithHomeFragment(): HomeFragment
    @ContributesAndroidInjector
    abstract fun provideWithNoteFragment(): AddNoteFragment
}