package com.akmisoftware.noteit.data.db

import androidx.room.*
import com.akmisoftware.noteit.data.model.Note
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flowable<MutableList<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteById(id: String): Single<Note>

    @Update
    fun editNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM note")
    fun deleteAll()
}