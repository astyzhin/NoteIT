package com.akmisoftware.noteit.di.builder

import androidx.lifecycle.ViewModel
import com.akmisoftware.noteit.di.utils.ViewModelKey
import com.akmisoftware.noteit.ui.HomeViewModel
import com.akmisoftware.noteit.ui.NoteViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AppViewModelBuilder {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
    abstract fun bindNoteViewModel(noteViewModel: NoteViewModel): ViewModel
}