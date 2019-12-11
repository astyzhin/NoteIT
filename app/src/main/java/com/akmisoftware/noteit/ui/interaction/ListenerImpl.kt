package com.akmisoftware.noteit.ui.interaction

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import com.akmisoftware.noteit.R
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.ui.MainActivity
import javax.inject.Inject


class ListenerImpl @Inject constructor(private var activity: MainActivity) :
    HomeListener, NoteListener {

    override fun homeToCreateNote() {
        findNavController(
            activity,
            R.id.nav_host_fragment
        ).navigate(R.id.action_nav_home_to_nav_add_note)
    }

    override fun homeToShowNote(note: Note) {
        val args = Bundle()
        args.putSerializable("show_note", note)
        findNavController(
            activity,
            R.id.nav_host_fragment
        ).navigate(R.id.action_nav_home_to_nav_show_note, args)
    }

    override fun homeToEditNote(id: String) {
        val args = Bundle()
        args.putString("edit_note", id)
        findNavController(
            activity,
            R.id.nav_host_fragment
        ).navigate(R.id.action_nav_home_to_nav_add_note, args)
    }

    override fun deleteNote(note: Note) {
    }

    override fun noteToHome() {
        findNavController(
            activity,
            R.id.nav_host_fragment
        ).popBackStack(R.id.nav_home, false)
    }

    override fun noteToEdit(id: String) {
        val args = Bundle()
        args.putString("edit_note", id)
        findNavController(
            activity,
            R.id.nav_host_fragment
        ).navigate(R.id.action_nav_show_note_to_nav_add_note, args)
    }
}