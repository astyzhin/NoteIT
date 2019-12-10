package com.akmisoftware.noteit.di

import com.akmisoftware.noteit.di.builder.MainActivityProvider
import com.akmisoftware.noteit.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityProvider::class])
    abstract fun bindMainActivity(): MainActivity
}