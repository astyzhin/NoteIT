package com.akmisoftware.noteit.data.repo

import com.akmisoftware.noteit.data.model.Note
import io.reactivex.Flowable
import io.reactivex.Single

interface NoteRepo {
    fun insertNote(note: Note)
    fun deleteNote(note: Note)
    fun deleteAll()
    fun editNote(note: Note)
    fun getAllNotes(): Flowable<MutableList<Note>>
    fun getNoteById(id: String): Single<Note>
}