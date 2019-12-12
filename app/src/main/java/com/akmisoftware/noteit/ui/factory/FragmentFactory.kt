package com.akmisoftware.noteit.ui.factory

import androidx.fragment.app.FragmentManager
import com.akmisoftware.noteit.ui.AddNoteFragment
import com.akmisoftware.noteit.ui.HomeFragment
import com.akmisoftware.noteit.ui.ShowNoteFragment

object FragmentFactory {

    fun getHomeFragment(supportFragmentManager: FragmentManager): HomeFragment {
        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.NAME)
        if (fragment == null) {
            fragment = HomeFragment()
        }
        return fragment as HomeFragment
    }

    fun getShowNoteFragment(supportFragmentManager: FragmentManager): ShowNoteFragment {
        var fragment = supportFragmentManager.findFragmentByTag(ShowNoteFragment.NAME)
        if (fragment == null){
            fragment = ShowNoteFragment()
        }
        return fragment as ShowNoteFragment
    }

    fun getAddNoteFragment(supportFragmentManager: FragmentManager): AddNoteFragment{
        var fragment = supportFragmentManager.findFragmentByTag(AddNoteFragment.NAME)
        if (fragment == null){
            fragment = AddNoteFragment()
        }
        return fragment as AddNoteFragment
    }
}