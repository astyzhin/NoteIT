package com.akmisoftware.noteit.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.data.repo.NoteRepo
import io.reactivex.Completable
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(var noteRepo: NoteRepo) : ViewModel() {
    fun deleteAllNotes(): Completable {
        return Completable.fromAction {
            noteRepo.deleteAll()
        }
    }

    fun deleteNote(note: Note): Completable {
        return Completable.fromAction {
            noteRepo.deleteNote(note)
        }
    }
}
