package com.akmisoftware.noteit.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.data.repo.NoteRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShowNoteViewModel @Inject constructor(var noteRepo: NoteRepo) : ViewModel() {

    fun getNoteById(id: String): LiveData<Note> {
        return noteRepo.getNoteById(id)
            .flatMap { Single.just(it) }
            .subscribeOn(Schedulers.io())
            .to { LiveDataReactiveStreams.fromPublisher(it.toFlowable()) }
    }
    fun deleteNote(note: Note): Completable {
        return Completable.fromAction {
            noteRepo.deleteNote(note)
        }
    }
}