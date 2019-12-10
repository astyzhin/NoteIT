package com.akmisoftware.noteit.data.repo

import com.akmisoftware.noteit.data.db.AppDatabase
import com.akmisoftware.noteit.data.model.Note
import io.reactivex.Flowable
import io.reactivex.Single

class NoteRepoImpl(private val database: AppDatabase) : NoteRepo {
    override fun insertNote(note: Note) {
        database.noteDao().insertNote(note)
    }

    override fun deleteNote(note: Note) {
        database.noteDao().deleteNote(note)
    }

    override fun deleteAll() {
        database.noteDao().deleteAll()
    }

    override fun editNote(note: Note) {
        database.noteDao().editNote(note)
    }

    override fun getAllNotes(): Flowable<MutableList<Note>> {
        return database.noteDao().getAllNotes()
    }

    override fun getNoteById(id: String): Single<Note> {
        return database.noteDao().getNoteById(id)
    }
}