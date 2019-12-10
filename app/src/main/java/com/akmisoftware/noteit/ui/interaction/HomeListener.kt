package com.akmisoftware.noteit.ui.interaction

import com.akmisoftware.noteit.data.model.Note

interface HomeListener {
    fun homeToCreateNote()
    fun homeToShowNote(note: Note)
    fun homeToEditNote(id: String)
    fun deleteNote(note: Note)
}