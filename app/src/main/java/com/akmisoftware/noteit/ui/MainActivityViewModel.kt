package com.akmisoftware.noteit.ui

import androidx.lifecycle.ViewModel
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.data.repo.NoteRepo
import io.reactivex.Completable
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(var noteRepository: NoteRepo) : ViewModel() {

    fun deleteAllNotes(): Completable {

        return Completable.fromAction {
            noteRepository.deleteAll()
        }
    }

    fun deleteNote(note: Note): Completable {

        return Completable.fromAction {
            noteRepository.deleteNote(note)
        }
    }
}
