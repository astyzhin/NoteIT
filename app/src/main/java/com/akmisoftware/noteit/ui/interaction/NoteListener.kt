package com.akmisoftware.noteit.ui.interaction

import android.net.Uri
import androidx.fragment.app.Fragment

interface NoteListener {
    fun noteToHome()
    fun noteToEdit(id: String)
    fun noteToCamera(fragment: Fragment, imageUri: Uri, requestCode: Int)
    fun noteToGallery(fragment: Fragment, requestCode: Int)
}