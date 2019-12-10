package com.akmisoftware.noteit.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akmisoftware.noteit.BR.item
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.databinding.ItemNoteBinding
import com.akmisoftware.noteit.ui.base.DataBindingViewHolder
import com.akmisoftware.noteit.ui.interaction.HomeListener

class HomeAdapter(private val notes: MutableList<Note>, private val homeListener: HomeListener?)

    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(notes[position])

    }

    fun add(list: MutableList<Note>) {
        notes.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        notes.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(dataBinding: ItemNoteBinding) :
        DataBindingViewHolder<Note>(dataBinding) {
        override fun onBind(t: Note): Unit = with(t) {
            dataBinding.setVariable(item, t)
        }

        init {

            dataBinding.homeListener = homeListener

        }

    }
}