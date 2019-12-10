package com.akmisoftware.noteit.di.module

import android.app.Application
import androidx.room.Room
import com.akmisoftware.noteit.data.db.AppDatabase
import com.akmisoftware.noteit.data.db.NoteDao
import com.akmisoftware.noteit.data.repo.NoteRepo
import com.akmisoftware.noteit.data.repo.NoteRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, AppDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(appDatabase: AppDatabase): NoteDao {
        return appDatabase.noteDao()
    }

    @Provides
    fun provideRepo(database: AppDatabase): NoteRepo {
        return NoteRepoImpl(database)
    }
}