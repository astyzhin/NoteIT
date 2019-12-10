package com.akmisoftware.noteit.data.repo

import androidx.lifecycle.LiveData
import com.akmisoftware.noteit.data.db.AppDatabase
import com.akmisoftware.noteit.data.model.Note

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

    override fun getAllNotes(): LiveData<List<Note>> {
        return database.noteDao().getAllNotes()
    }

    override fun getNoteById(id: Int): LiveData<Note> {
        return database.noteDao().getNoteById(id)
    }
}