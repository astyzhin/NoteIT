package com.akmisoftware.noteit.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.data.repo.NoteRepo
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(var noteRepo: NoteRepo) : ViewModel() {
    fun getAllNotes(): LiveData<MutableList<Note>> {
        return noteRepo.getAllNotes()
            .onErrorReturn { Collections.emptyList() }
            .subscribeOn(Schedulers.io())
            .to { LiveDataReactiveStreams.fromPublisher(it) }
    }
}