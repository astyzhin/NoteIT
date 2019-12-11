package com.akmisoftware.noteit.ui.interaction

interface NoteListener {
    fun noteToHome()

    fun noteToEdit(id: String)
}