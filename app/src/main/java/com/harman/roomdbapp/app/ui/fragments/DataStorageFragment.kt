package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.harman.roomdbapp.app.adapters.DocumentsAdapter
import com.harman.roomdbapp.app.adapters.callbaks.SwipeToDeleteCallback
import com.harman.roomdbapp.app.databinding.FragmentDataStorageBinding
import com.harman.roomdbapp.domain.model.Document

class DataStorageFragment : Fragment() {

    lateinit var binding: FragmentDataStorageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDataStorageBinding.inflate(inflater, container, false)

        val adapter = DocumentsAdapter(requireContext()) {
            val btnSheet = BottomSheetFragment.newInstance(it)
            btnSheet.show(parentFragmentManager, "Modal Bottom Sheet")
        }

        val itemTouchHelper = ItemTouchHelper(
            SwipeToDeleteCallback {
                val newList = adapter.currentList.toMutableList()
                newList.removeAt(it)
                adapter.submitList(newList)
                // TODO delete value
            }
        )

        binding.btBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.rvDocList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
            itemTouchHelper.attachToRecyclerView(this)
        }
        // TODO inflate with real value
        adapter.submitList(
            listOf(
                Document("1855513256.csv"),
                Document("1855211100000000000000000003256.csv")
            )
        )

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = DataStorageFragment()
    }
}
