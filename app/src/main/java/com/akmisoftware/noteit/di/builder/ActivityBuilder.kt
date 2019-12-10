package com.akmisoftware.noteit.di.builder

import com.akmisoftware.noteit.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityProvider::class])
    abstract fun bindMainActivity(): MainActivity
}