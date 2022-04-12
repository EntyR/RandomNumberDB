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
import com.harman.roomdbapp.app.ui.viewmodel.DataStorageViewModel
import com.harman.roomdbapp.domain.model.Document
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataStorageFragment : Fragment() {

    lateinit var binding: FragmentDataStorageBinding
    private val viewModel: DataStorageViewModel by viewModel()

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
                viewModel.deleteGravityDocument(adapter.getDocName(it))
                adapter.deleteItem(it)
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

        adapter.submitList(
            viewModel.getGravityDocumentNameList().map {
                Document(it)
            }
        )

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = DataStorageFragment()
    }
}
