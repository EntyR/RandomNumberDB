package com.harman.roomdbapp.app.adapters

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateMargins
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.WidgetItemBinding
import com.harman.roomdbapp.app.model.Widget

class WidgetAdapter(
    private val ctx: Context,
    private val recyclerWidth: Int,
    private val callback: (widgetName: String) -> Unit
) : ListAdapter<Widget, WidgetAdapter.WidgetViewHolder>(Companion) {


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

        val linesAmount = currentList[position].text.split("\n").size

        val baseMargin = ctx.resources.getDimensionPixelOffset(R.dimen.widget_offset)

        val elemSize = recyclerWidth / 1.7

        holder.binding.root.apply {
            val params = this.layoutParams as RecyclerView.LayoutParams
            params.width = elemSize.toInt()
            params.height = elemSize.toInt()
            setLayoutParams(params)

            setOnClickListener {
                callback(holder.binding.tvWedgetName.text.toString())
            }
        }
        holder.binding.tvWedgetName.apply {



            val margin = this.layoutParams as ViewGroup.MarginLayoutParams
            margin.updateMargins(
                top = ((elemSize / 10) / (linesAmount / 2)).toInt(),
                bottom = (elemSize / 8).toInt()
            )

            val params = margin as ConstraintLayout.LayoutParams
            params.width = (elemSize / 2.5).toInt()
            setLayoutParams(params)
        }

        holder.binding.tvWedgetName.maxLines = linesAmount

        holder.binding.ivWidgetImage.apply {
            val params = this.layoutParams as ConstraintLayout.LayoutParams
            params.width = (elemSize / 2.8).toInt()
            setLayoutParams(params)

            val margin = this.layoutParams as ViewGroup.MarginLayoutParams
            margin.updateMargins(left = (elemSize / 8).toInt(), top = (elemSize / 13).toInt())
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


}
