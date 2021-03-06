package com.harman.roomdbapp.app.ui.fragments

import android.app.Dialog
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.adapters.DocumentsEntryAdapter
import com.harman.roomdbapp.app.databinding.FragmentBottomSheetListDialogBinding
import com.harman.roomdbapp.app.ui.viewmodel.BottomSheetViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val DOC_NAME = "doc_name"

class BottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentBottomSheetListDialogBinding

    private val viewModel: BottomSheetViewModel by viewModel()

    private var docName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        docName = arguments?.getString(DOC_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBottomSheetListDialogBinding.inflate(inflater, container, false)

        binding.tvNumberValue.text = getString(R.string.document, docName ?: ".csv")

        binding.tvNumberValue.isSelected = true
        val adapter = DocumentsEntryAdapter()
        binding.list.apply {

            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        docName?.let {
            viewModel.getGravityRecordsFromDocuments(it).let { records ->
                adapter.submitList(records)
            }
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val height = Resources.getSystem().displayMetrics.heightPixels

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            (it as BottomSheetDialog).behavior.apply {
                state = BottomSheetBehavior.STATE_SETTLING
                isHideable = true
                isFitToContents = false
                maxHeight = height
                peekHeight = (height / 1.2).toInt()
                skipCollapsed = true
            }

            val params = (binding.root.layoutParams as FrameLayout.LayoutParams)
            params.height = height
            binding.root.layoutParams = params
        }
        return dialog
    }

    companion object {

        fun newInstance(docName: String): BottomSheetFragment =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(DOC_NAME, docName)
                }
            }
    }
}
