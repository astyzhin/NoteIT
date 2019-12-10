package com.akmisoftware.noteit.data.repo

import androidx.lifecycle.LiveData
import com.akmisoftware.noteit.data.model.Note

interface NoteRepo {

    fun insertNote(note: Note)
    fun deleteNote(note: Note)
    fun deleteAll()
    fun editNote(note: Note)
    fun getAllNotes(): LiveData<List<Note>>
    fun getNoteById(id: Int): LiveData<Note>

}