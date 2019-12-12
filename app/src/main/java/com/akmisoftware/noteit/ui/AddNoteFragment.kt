package com.akmisoftware.noteit.ui


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val REQUEST_IMAGE_FROM_GALLERY = 1
private const val REQUEST_IMAGE_CAPTURE = 2
private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS =
    arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)

class AddNoteFragment : DaggerFragment() {
    companion object {
        val NAME: String = AddNoteFragment::class.java.name
        private val TAG = AddNoteFragment::class.java.simpleName
    }

    private var compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AddNoteViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(AddNoteViewModel::class.java)
    }
    private lateinit var noteListener: NoteListener

    private lateinit var imageUri: Uri

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
            if (!edit_title.text.isNullOrBlank() || !edit_description.text.isNullOrBlank()) {
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
                noteListener.noteToHome()
            } else {
                Toast.makeText(context, "Can't save empty note", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnGallery.setOnClickListener {
            Log.d(TAG, "gallery button clicked")
            noteListener.noteToGallery(this, REQUEST_IMAGE_FROM_GALLERY)
        }

        binding.btnCamera.setOnClickListener {
            Log.d(TAG, "camera button clicked")
            val timeStamp =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val outputImage = File(activity!!.externalCacheDir, "${timeStamp}.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= 24) {
                FileProvider.getUriForFile(
                    context!!,
                    "com.akmisoftware.noteit.fileprovider",
                    outputImage
                )
            } else {
                Uri.fromFile(outputImage)
            }
            noteListener.noteToCamera(this, imageUri, REQUEST_IMAGE_CAPTURE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_IMAGE_FROM_GALLERY ->
                if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
                    val imagePath = data.data
                    try {
                        imagePath?.let {
                            if (Build.VERSION.SDK_INT < 28) {
                                @Suppress("DEPRECATION")
                                val imageBmp = MediaStore.Images.Media.getBitmap(
                                    context?.contentResolver,
                                    imagePath
                                )
                                imageView.apply {
                                    visibility = View.VISIBLE
                                    setImageBitmap(imageBmp)
                                }
                            } else {
                                val imageSource =
                                    ImageDecoder.createSource(context!!.contentResolver, imagePath)
                                val imageBmp = ImageDecoder.decodeBitmap(imageSource)
                                imageView.apply {
                                    visibility = View.VISIBLE
                                    setImageBitmap(imageBmp)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "error loading image from gallery: ${e.localizedMessage}")
                    }
                }
            REQUEST_IMAGE_CAPTURE ->
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        if (Build.VERSION.SDK_INT < 28) {
                            @Suppress("DEPRECATION")
                            val imageBmp =
                                MediaStore.Images.Media.getBitmap(
                                    context?.contentResolver,
                                    imageUri
                                )
                            Log.d(TAG, "SDK < 28 $imageBmp, $imageUri")
                            imageView.apply {
                                visibility = View.VISIBLE
                                setImageBitmap(imageBmp)
                            }
                        } else {
                            val imageBmp = BitmapFactory.decodeStream(
                                activity!!.contentResolver.openInputStream(imageUri)
                            )
                            Log.d(TAG, "SDK > 28 $imageBmp, $imageUri")
                            imageView.apply {
                                visibility = View.VISIBLE
                                setImageBitmap(imageBmp)
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "error loading image from $imageUri: ${e.localizedMessage}")
                    }
                }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NoteListener) {
            noteListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        compositeDisposable.clear()
    }
}
