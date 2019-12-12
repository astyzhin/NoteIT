package com.akmisoftware.noteit.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.akmisoftware.noteit.R
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.databinding.ActivityMainBinding
import com.akmisoftware.noteit.ui.interaction.HomeListener
import com.akmisoftware.noteit.ui.interaction.ListenerImpl
import com.akmisoftware.noteit.ui.interaction.NoteListener
import com.akmisoftware.noteit.ui.viewmodels.MainActivityViewModel
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity @Inject constructor() : DaggerAppCompatActivity(), HomeListener, NoteListener {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private var compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
    }

    private val listenerImpl: ListenerImpl = ListenerImpl(this)

    override fun deleteNote(note: Note) {
        compositeDisposable.add(viewModel.deleteNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "DELETE: deleted successfully")
            }, { t: Throwable? ->
                Log.d(TAG, "DELETE: ${t?.message}")
            })
        )
    }

    override fun homeToEditNote(id: String) {
        listenerImpl.homeToEditNote(id)
    }

    override fun homeToCreateNote() {
        listenerImpl.homeToCreateNote()
    }

    override fun homeToShowNote(note: Note) {
        listenerImpl.homeToShowNote(note)
    }

    override fun noteToHome() {
        listenerImpl.noteToHome()
    }

    override fun noteToEdit(id: String) {
        listenerImpl.noteToEdit(id)
    }

    override fun noteToCamera(fragment: Fragment, imageUri: Uri, requestCode: Int) {
        listenerImpl.noteToCamera(fragment, imageUri, requestCode)
    }

    override fun noteToGallery(fragment: Fragment, requestCode: Int) {
        listenerImpl.noteToGallery(fragment, requestCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_delete -> {
                compositeDisposable.add(viewModel.deleteAllNotes()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.d(TAG, "DELETE: deleted successfully")
                    }, { t: Throwable? ->
                        Log.d(TAG, "DELETE: ${t?.message}")
                    })
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}