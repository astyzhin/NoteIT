package com.akmisoftware.noteit.ui.interaction

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
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

    override fun noteToCamera(fragment: Fragment, imageUri: Uri, requestCode: Int) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        fragment.startActivityForResult(intent, requestCode)
    }

    override fun noteToGallery(fragment: Fragment, requestCode: Int) {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
        }
        fragment.startActivityForResult(
            Intent.createChooser(intent, "Select Image"),
            requestCode
        )
    }
}