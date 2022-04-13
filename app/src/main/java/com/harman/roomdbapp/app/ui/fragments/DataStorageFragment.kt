package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.harman.roomdbapp.app.adapters.DocumentsAdapter
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
            object : ItemTouchHelper.Callback() {
                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    return makeFlag(ACTION_STATE_IDLE, RIGHT) or makeFlag(
                        ACTION_STATE_SWIPE,
                        LEFT or RIGHT
                    )
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    adapter.switchEnabled()
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val pos = viewHolder.adapterPosition
                    viewModel.deleteGravityDocument(adapter.getDocName(pos))
                    adapter.deleteItem(pos)
                    adapter.switchEnabled()

                }

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
