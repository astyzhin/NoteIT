package com.akmisoftware.noteit.ui

import androidx.lifecycle.LiveData
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.data.repo.NoteRepo
import com.akmisoftware.noteit.ui.base.BaseViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(var noteRepo: NoteRepo) : BaseViewModel() {

    fun getAllNotes(): LiveData<List<Note>> {
        return noteRepo.getAllNotes()
    }
    fun deleteNote(note: Note) {
        noteRepo.deleteNote(note)
    }
    fun deleteAll() {
        noteRepo.deleteAll()
    }
}