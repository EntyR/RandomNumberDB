package com.harman.roomdbapp.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.databinding.RandomNumberListItemBinding
import com.harman.roomdbapp.domain.model.RandomNumber

class NumberListAdapter(private val itemPressedCallback: (number: Int)->Unit): ListAdapter<RandomNumber, NumberListAdapter.RandomNumberViewHolder>(Companion) {

    class RandomNumberViewHolder(val binding: RandomNumberListItemBinding): RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<RandomNumber>() {
        override fun areItemsTheSame(oldItem: RandomNumber, newItem: RandomNumber): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RandomNumber, newItem: RandomNumber): Boolean {
            return oldItem.number == newItem.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomNumberViewHolder {
        return RandomNumberViewHolder(RandomNumberListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RandomNumberViewHolder, position: Int) {

        holder.binding.btItemValue.text = currentList[position].number.toString()
        holder.binding.btItemValue.setOnClickListener {
            itemPressedCallback(currentList[position].number)
        }

    }

}