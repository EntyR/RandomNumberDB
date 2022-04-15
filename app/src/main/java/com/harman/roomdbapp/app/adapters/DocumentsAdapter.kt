package com.harman.roomdbapp.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.DataStorageItemBinding
import com.harman.roomdbapp.domain.model.Document

class DocumentsAdapter(
    private val ctx: Context,
    private val itemPressedCallback: (docName: String) -> Unit
) :
    RecyclerView.Adapter<DocumentsAdapter.DocumentViewHolder>() {

    private var isClickEnabled = true

    private var currentList = mutableListOf<Document>()

    class DocumentViewHolder(val binding: DataStorageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

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

    fun getDocName(index: Int): String {
        return currentList[index].docName
    }

    fun submitList(list: List<Document>) {
        currentList = list.toMutableList()
    }

    override fun onBindViewHolder(holder: DocumentViewHolder, position: Int) {
        val numberString = ctx.getString(R.string.document, currentList[position].docName)
        holder.binding.tvValue.text = numberString

        holder.binding.tvValue.isSelected = true

        holder.binding.btItemValue.setOnClickListener {
            if (isClickEnabled)
                itemPressedCallback(currentList[position].docName)
        }
    }

    fun switchEnabled(value: Boolean) {
        isClickEnabled = value
    }

    fun deleteItem(position: Int) {
        notifyItemRangeChanged(position, currentList.size)
        currentList.removeAt(position)
        notifyItemRemoved(position)
    }
}
