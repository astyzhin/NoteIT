package com.akmisoftware.noteit

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.akmisoftware.noteit.data.db.AppDatabase
import com.akmisoftware.noteit.data.db.NoteDao
import com.akmisoftware.noteit.data.model.Note
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private var noteDao: NoteDao? = null
    private var db: AppDatabase? = null

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        noteDao = db?.noteDao()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun shouldInsertNoteItem() {
        val note = Note("1", "Hello world", "This is a helloworld note")
        noteDao?.insertNote(note)
        val noteTest = (noteDao?.getNoteById(note.id)!!).blockingGet()
        Assert.assertEquals(note.title, noteTest.title)
    }

    @Test
    fun shouldFlushDB() {
        noteDao?.deleteAll()
        Assert.assertEquals(noteDao?.getAllNotes()!!.blockingFirst().count(), 0)
    }


    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(t: T?) {
                data[0] = t
                latch.countDown()
                liveData.removeObserver(this)//To change body of created functions use File | Settings | File Templates.
            }

        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }


    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)

        val observer = Observer<T> { t ->
            value = t
            latch.countDown()
        }

        observeForever(observer)

        latch.await(2, TimeUnit.SECONDS)
        return value
    }
}