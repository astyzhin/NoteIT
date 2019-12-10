package com.akmisoftware.noteit.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.akmisoftware.noteit.data.model.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotes(notes: List<Note>)

    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: Int): LiveData<Note>

    @Update
    fun editNote(note: Note)

    @Delete
    fun deleteNote(note: Note): LiveData<Note>

    @Query("DELETE FROM note")
    fun deleteAll(): LiveData<List<Note>>
}