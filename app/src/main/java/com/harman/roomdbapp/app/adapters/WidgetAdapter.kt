package com.harman.roomdbapp.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.WidgetItemBinding
import com.harman.roomdbapp.app.model.Widget

// TODO Add button pressed callback

class WidgetAdapter(private val ctx: Context, private val recyclerWidth: Int) : ListAdapter<Widget, WidgetAdapter.WidgetViewHolder>(Companion) {

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

        val layoutParams = holder.binding.root.layoutParams as ViewGroup.MarginLayoutParams

        val baseMargin = ctx.resources.getDimensionPixelOffset(R.dimen.widget_offset)

        val elemSize = recyclerWidth / 1.7

        holder.binding.root.apply {
            val params = this.layoutParams as RecyclerView.LayoutParams
            params.width = elemSize.toInt()
            params.height = elemSize.toInt()
            setLayoutParams(params)
        }

        when (position) {
            0 -> {
                val offset = (recyclerWidth - elemSize) / 2
                layoutParams.updateMargins(left = offset.toInt(), right = baseMargin)
            }
            currentList.size - 1 -> {
                val offset = (recyclerWidth - elemSize) / 2
                layoutParams.updateMargins(right = offset.toInt(), left = baseMargin)
            }
            else -> layoutParams.updateMargins(left = baseMargin, right = baseMargin)
        }

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
