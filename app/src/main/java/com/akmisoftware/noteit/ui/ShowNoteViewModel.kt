package com.akmisoftware.noteit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.data.repo.NoteRepo
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShowNoteViewModel @Inject constructor(var noteRepository: NoteRepo) : ViewModel() {

    fun getNoteById(id: String): LiveData<Note> {
        return noteRepository.getNoteById(id)
            .flatMap { Single.just(it) }
            .subscribeOn(Schedulers.io())
            .to { LiveDataReactiveStreams.fromPublisher(it.toFlowable()) }
    }
}