package com.akmisoftware.noteit.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.akmisoftware.noteit.R
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.databinding.FragmentShowNoteBinding
import com.akmisoftware.noteit.ui.interaction.NoteListener
import com.akmisoftware.noteit.ui.viewmodels.ShowNoteViewModel
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShowNoteFragment : DaggerFragment() {
    companion object {
        val NAME: String = ShowNoteFragment::class.java.name
    }

    private var compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var noteListener: NoteListener

    private val viewModel: ShowNoteViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(ShowNoteViewModel::class.java)
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
        binding.btnDelete.setOnClickListener {
            compositeDisposable.add(viewModel.deleteNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(NAME, "DELETE: deleted successfully")

                }, {t: Throwable? ->
                    Log.d(NAME,"DELETE: ${t?.message}")
                }))
            noteListener.noteToHome()
        }
        binding.btnEdit.setOnClickListener {
            noteListener.noteToEdit(note.id)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NoteListener) {
            noteListener = context
        }
    }
    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }
}