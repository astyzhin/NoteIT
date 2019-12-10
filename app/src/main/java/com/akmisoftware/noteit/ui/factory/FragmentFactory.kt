package com.akmisoftware.noteit.ui.factory

import androidx.fragment.app.FragmentManager
import com.akmisoftware.noteit.ui.HomeFragment
import com.akmisoftware.noteit.ui.NoteFragment

object FragmentFactory {

    fun getHomeFragment(fragmentManager: FragmentManager): HomeFragment {
        var fragment = fragmentManager.findFragmentByTag(HomeFragment.NAME)
        if (fragment == null) {
            fragment = HomeFragment()
        }
        return fragment as HomeFragment
    }
    fun getNoteFragment(fragmentManager: FragmentManager): NoteFragment {
        var fragment = fragmentManager.findFragmentByTag(NoteFragment.NAME)
        if (fragment == null) {
            fragment = NoteFragment()
        }
        return fragment as NoteFragment
    }
}