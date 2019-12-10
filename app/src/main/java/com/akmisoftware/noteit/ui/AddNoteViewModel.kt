package com.akmisoftware.noteit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.data.repo.NoteRepo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddNoteViewModel @Inject constructor(var noteRepository: NoteRepo) : ViewModel() {


    fun insertNote(note: Note): Completable {
        return Completable.fromAction {
            noteRepository.insertNote(note)
        }
    }

    fun editNote(note: Note): Completable {
        return Completable.fromAction {
            noteRepository.editNote(note)
        }
    }

    fun getNoteById(id: String): LiveData<Note> {
        return noteRepository.getNoteById(id)
            .flatMap { Single.just(it) }
            .subscribeOn(Schedulers.io())
            .to { LiveDataReactiveStreams.fromPublisher(it.toFlowable()) }
    }
}