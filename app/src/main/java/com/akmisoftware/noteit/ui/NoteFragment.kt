package com.akmisoftware.noteit.ui


import androidx.fragment.app.Fragment
import dagger.android.support.DaggerFragment

/**
 * A simple [Fragment] subclass.
 */
class NoteFragment : DaggerFragment() {
    companion object {
        val NAME: String = NoteFragment::class.java.simpleName
    }
}
