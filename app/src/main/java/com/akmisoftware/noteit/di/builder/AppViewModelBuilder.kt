package com.akmisoftware.noteit.di.builder

import androidx.lifecycle.ViewModel
import com.akmisoftware.noteit.ui.viewmodels.AddNoteViewModel
import com.akmisoftware.noteit.ui.viewmodels.HomeViewModel
import com.akmisoftware.noteit.ui.viewmodels.MainActivityViewModel
import com.akmisoftware.noteit.ui.viewmodels.ShowNoteViewModel
import com.akmisoftware.noteit.utils.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AppViewModelBuilder {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddNoteViewModel::class)
    abstract fun bindAddNoteViewModel(addNoteViewModel: AddNoteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShowNoteViewModel::class)
    abstract fun bindShowNoteViewModel(showNoteViewModel: ShowNoteViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

}