package com.akmisoftware.noteit.ui


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.akmisoftware.noteit.R
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.databinding.FragmentAddNoteBinding
import com.akmisoftware.noteit.ui.interaction.NoteListener
import com.akmisoftware.noteit.ui.viewmodels.AddNoteViewModel
import com.akmisoftware.noteit.utils.hideKeyboard
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

private const val REQUEST_IMAGE_CAPTURE = 2
private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS =
    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

class AddNoteFragment : DaggerFragment() {

    companion object {
        val NAME: String = AddNoteFragment::class.java.name
        private val TAG: String = AddNoteFragment::class.java.simpleName
    }

    private var noteInteractionListener: NoteListener? = null

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: AddNoteViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(
            AddNoteViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val id = arguments?.getString("edit_note")

        val binding: FragmentAddNoteBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_note, container, false)
        binding.lifecycleOwner = this

        if (id != null) {
            viewModel.getNoteById(id).observe(viewLifecycleOwner, Observer {
                binding.editTitle.text = Editable.Factory.getInstance().newEditable(it.title)
                binding.editDescription.text =
                    Editable.Factory.getInstance().newEditable(it.body)
            })
        }

        binding.btnSave.setOnClickListener {
            if (id == null) {
                compositeDisposable.add(
                    viewModel.insertNote(
                        Note(
                            UUID.randomUUID().toString(),
                            edit_title.text.toString(), edit_description.text.toString()
                        )
                    )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({

                            Log.d(TAG, "INSERT: item inserted in table")
                        }, { throwable ->

                            Log.e(TAG, "Error: INSERT " + throwable.message)
                        })
                )
            } else {
                compositeDisposable.add(
                    viewModel.editNote(
                        Note(
                            id,
                            edit_title.text.toString(),
                            edit_description.text.toString()
                        )
                    )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe({
                            Log.d(TAG, "INSERT: item inserted in table")
                        }, { throwable ->
                            Log.e(TAG, "Error: INSERT " + throwable.message)
                        })
                )
            }
            it.hideKeyboard()
            noteInteractionListener?.noteToHome()
        }
        binding.btnGallery.setOnClickListener {
            Log.d(TAG, "gallery button clicked")
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            }
            val fragment = this
            fragment.startActivityForResult(
                Intent.createChooser(intent, "Select Image"),
                REQUEST_IMAGE_CAPTURE
            )
        }
        binding.btnCamera.setOnClickListener {
            Log.d(TAG, "camera button clicked")
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val fragment = this
            fragment.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE && data != null && data.data != null) {
            val imagePath = data.data
            val imageBmp = MediaStore.Images.Media.getBitmap(context?.contentResolver, imagePath)
            val outputStream = ByteArrayOutputStream()
            imageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            val imageBytes = outputStream.toByteArray()
            Log.d(TAG, "$imageBytes")
            imageView.apply {
                visibility = View.VISIBLE
                setImageBitmap(imageBmp)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is NoteListener) {
            noteInteractionListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        noteInteractionListener = null
        compositeDisposable.clear()
    }
}
