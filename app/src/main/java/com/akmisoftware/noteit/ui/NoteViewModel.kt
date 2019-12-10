package com.akmisoftware.noteit.ui

import androidx.lifecycle.LiveData
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.data.repo.NoteRepo
import com.akmisoftware.noteit.ui.base.BaseViewModel
import javax.inject.Inject

class NoteViewModel @Inject constructor(var noteRepo: NoteRepo) : BaseViewModel() {

    fun getNoteById(id: Int): LiveData<Note> {
        return noteRepo.getNoteById(id)
    }

}