package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.harman.roomdbapp.app.adapters.DocumentsAdapter
import com.harman.roomdbapp.app.databinding.FragmentDataStorageBinding
import com.harman.roomdbapp.domain.model.Document


class DataStorageFragment : Fragment() {


    lateinit var binding: FragmentDataStorageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDataStorageBinding.inflate(inflater, container, false)

        val adapter = DocumentsAdapter(requireContext()) {
            val btnSheet = BottomSheetFragment.newInstance("1855513256.csv")
            btnSheet.show(parentFragmentManager, "Modal Bottom Sheet")
        }
        binding.rvDocList.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())

        }
        //TODO inflate with real value
        adapter.submitList(listOf(Document("1855513256.csv")))

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = DataStorageFragment()
    }
}