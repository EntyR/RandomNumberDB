package com.harman.roomdbapp.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.RandomNumberListItemBinding
import com.harman.roomdbapp.domain.model.RandomNumber

class NumberListAdapter(val context: Context, private val itemPressedCallback: (number: Int) -> Unit) :
    ListAdapter<RandomNumber, NumberListAdapter.RandomNumberViewHolder>(Companion) {

    class RandomNumberViewHolder(val binding: RandomNumberListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<RandomNumber>() {
        override fun areItemsTheSame(oldItem: RandomNumber, newItem: RandomNumber): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: RandomNumber, newItem: RandomNumber): Boolean {
            return oldItem.number == newItem.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomNumberViewHolder {
        return RandomNumberViewHolder(
            RandomNumberListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RandomNumberViewHolder, position: Int) {
        val numberString = context.getString(R.string.number, currentList[position].number)
        holder.binding.btItemValue.text = numberString
        holder.binding.btItemValue.setOnClickListener {
            itemPressedCallback(currentList[position].number)
        }

    }

}