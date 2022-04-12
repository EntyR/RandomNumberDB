package com.harman.roomdbapp.app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.app.databinding.FragmentBottomSheetListDialogItemBinding
import com.harman.roomdbapp.app.other.getFormattedDateFromMillis
import com.harman.roomdbapp.domain.model.GravityRecord
import java.util.Collections

class DocumentsEntryAdapter :
    ListAdapter<GravityRecord?, DocumentsEntryAdapter.DocumentEntryViewHolder>(Companion) {

    class DocumentEntryViewHolder(val binding: FragmentBottomSheetListDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<GravityRecord?>() {
        override fun areItemsTheSame(oldItem: GravityRecord, newItem: GravityRecord): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GravityRecord, newItem: GravityRecord): Boolean {
            return (oldItem.timestamp == newItem.timestamp) && (oldItem.record == newItem.record)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentEntryViewHolder {
        return DocumentEntryViewHolder(
            FragmentBottomSheetListDialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DocumentEntryViewHolder, position: Int) {
        val value = currentList[position]?.record?.toInt() ?: ""
        val date =
            if (currentList[position] != null) getFormattedDateFromMillis(currentList[position]!!.timestamp)
            else ""
        holder.binding.tvValue.text = value.toString()
        holder.binding.tvDate.text = date
    }

    override fun submitList(list: List<GravityRecord?>?) {

        val newList = list?.toMutableList() ?: mutableListOf()
        if (newList.size < 4) newList.addAll(Collections.nCopies(4 - newList.size, null))
        super.submitList(newList.toList())
    }
}
