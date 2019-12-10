package com.akmisoftware.noteit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.akmisoftware.noteit.R
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.databinding.FragmentShowNoteBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ShowNoteFragment : DaggerFragment() {

    companion object {
        val NAME: String = ShowNoteFragment::class.java.name
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: ShowNoteViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(
            ShowNoteViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val note: Note = arguments?.getSerializable("show_note") as Note
        val binding: FragmentShowNoteBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_note, container, false)
        binding.lifecycleOwner = this
        binding.note = note
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}