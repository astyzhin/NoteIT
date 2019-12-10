package com.akmisoftware.noteit.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akmisoftware.noteit.data.model.Note

@Database(entities = [Note::class], version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
        const val NAME = "note.db"
    }

    abstract fun noteDao(): NoteDao
}