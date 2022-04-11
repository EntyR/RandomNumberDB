package com.harman.roomdbapp.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.DataStorageItemBinding
import com.harman.roomdbapp.domain.model.Document

class DocumentsAdapter(
    private val ctx: Context,
    private val itemPressedCallback: (docName: String) -> Unit
) :
    RecyclerView.Adapter<DocumentsAdapter.DocumentViewHolder>() {

    private var currentList = mutableListOf<Document>()

    class DocumentViewHolder(val binding: DataStorageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<Document>() {
        override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
            return oldItem.docName == newItem.docName
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocumentViewHolder {
        return DocumentViewHolder(
            DataStorageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun submitList(list: List<Document>) {
        currentList = list.toMutableList()
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val numberString = ctx.getString(R.string.document, currentList[position].docName)
        holder.binding.tvValue.text = numberString

        holder.binding.tvValue.isSelected = true

        holder.binding.btItemValue.setOnClickListener {
            itemPressedCallback(currentList[position].docName)
        }
    }

    fun deleteItem(position: Int) {

        currentList.removeAt(position)
        notifyItemRemoved(position)
    }
}
