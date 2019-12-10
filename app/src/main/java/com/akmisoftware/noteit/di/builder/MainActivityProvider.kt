package com.akmisoftware.noteit.di.builder

import com.akmisoftware.noteit.ui.HomeFragment
import com.akmisoftware.noteit.ui.NoteFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityProvider {
    @ContributesAndroidInjector
    abstract fun provideWithHomeFragment(): HomeFragment

    abstract fun provideWithNoteFragment(): NoteFragment
}