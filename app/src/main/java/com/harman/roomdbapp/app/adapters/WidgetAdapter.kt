package com.harman.roomdbapp.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.app.databinding.WidgetItemBinding
import com.harman.roomdbapp.app.model.Widget

// TODO Add button pressed callback

class WidgetAdapter : ListAdapter<Widget, WidgetAdapter.WidgetViewHolder>(Companion) {

    class WidgetViewHolder(val binding: WidgetItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<Widget>() {
        override fun areItemsTheSame(oldItem: Widget, newItem: Widget): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Widget, newItem: Widget): Boolean {
            return (oldItem.imageResourceId == newItem.imageResourceId) &&
                (oldItem.text == newItem.text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WidgetViewHolder {
        return WidgetViewHolder(
            WidgetItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WidgetViewHolder, position: Int) {

        val item = currentList[position]

        holder.binding.ivWidgetImage.setImageResource(item.imageResourceId)
        holder.binding.tvWedgetName.text = item.text
    }

    // For some reason it crashed if i didn't include this

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItem(position: Int): Widget {
        return super.getItem(position)
    }
}
