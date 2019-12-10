package com.akmisoftware.noteit.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.akmisoftware.noteit.BR.item
import com.akmisoftware.noteit.data.model.Note
import com.akmisoftware.noteit.databinding.ItemNoteBinding
import com.akmisoftware.noteit.ui.base.DataBindingViewHolder

class HomeAdapter(private val notes: List<Note>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        holder.onBind(notes[position])
    }

    inner class ViewHolder(dataBinding: ViewDataBinding) :
        DataBindingViewHolder<Note>(dataBinding) {
        override fun onBind(t: Note): Unit = with(t) {
            dataBinding.setVariable(item, t)
        }
    }
}