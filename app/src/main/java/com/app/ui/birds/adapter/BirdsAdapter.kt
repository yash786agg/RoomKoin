package com.app.ui.birds.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.callBacks.DiffUtilCallback
import com.app.model.birds.BirdsEntity
import com.app.roomkoin.R
import com.app.roomkoin.databinding.AdapterBirdsBinding
import kotlin.properties.Delegates

class BirdsAdapter : RecyclerView.Adapter<BirdsAdapter.MyViewHolder>(), DiffUtilCallback {

    var items: List<BirdsEntity> by Delegates.observable(emptyList()) { _, oldItem, newItem ->
        autoNotify(oldItem, newItem) { old, new -> old.timeStamp == new.timeStamp }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: AdapterBirdsBinding = DataBindingUtil
            .inflate(LayoutInflater.from(parent.context), R.layout.adapter_birds, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    // Gets the number of Items in the list
    override fun getItemCount(): Int = items.size

    inner class MyViewHolder(private val binding: AdapterBirdsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(birdsEntity: BirdsEntity) {
            binding.entity = birdsEntity
        }
    }
}
