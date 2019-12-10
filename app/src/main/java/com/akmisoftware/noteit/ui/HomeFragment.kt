package com.akmisoftware.noteit.ui


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.akmisoftware.noteit.R
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.databinding.FragmentHomeBinding
import com.akmisoftware.noteit.ui.adapters.HomeAdapter
import com.akmisoftware.noteit.ui.interaction.HomeListener
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : DaggerFragment() {
    companion object {
        val NAME: String = HomeFragment::class.java.simpleName
    }

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var homeListener: HomeListener? = null

    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private val adapter: HomeAdapter by lazy { HomeAdapter(arrayListOf(), homeListener) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            inflate(inflater, R.layout.fragment_home, container, false)

        binding.lifecycleOwner = this

        binding.listener = homeListener

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {

            getAllNotes().observe(viewLifecycleOwner, Observer {
                initView(it)
            })
        }
    }


    private fun initView(it: MutableList<Note>) {
        recycler_home.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recycler_home.adapter = adapter

        if (it.isNotEmpty()) {
            adapter.clear()
            adapter.add(it)

        } else {
            adapter.clear()
            Toast.makeText(context, context?.getString(R.string.empty_list), Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is HomeListener) {
            homeListener = context

        } else {
            throw RuntimeException(context.toString() + " must implement HomeInteraction")

        }
    }

    override fun onDetach() {
        super.onDetach()
        homeListener = null
        compositeDisposable.dispose()
    }
}
