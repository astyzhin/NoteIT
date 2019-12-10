package com.akmisoftware.noteit.di.builder

import com.akmisoftware.noteit.ui.AddNoteFragment
import com.akmisoftware.noteit.ui.HomeFragment
import com.akmisoftware.noteit.ui.ShowNoteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityProvider {
    @ContributesAndroidInjector
    abstract fun provideWithHomeFragment(): HomeFragment
    @ContributesAndroidInjector
    abstract fun provideWithCreateNoteFragment(): AddNoteFragment
    @ContributesAndroidInjector
    abstract fun provideWithShowNoteFragment(): ShowNoteFragment
}