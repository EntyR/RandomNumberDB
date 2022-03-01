package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.harman.roomdbapp.R
import com.harman.roomdbapp.app.adapters.NumberListAdapter
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import com.harman.roomdbapp.databinding.FragmentRandomNumbersListBinding
import org.koin.android.ext.android.inject

class RandomNumbersList : Fragment() {

    private lateinit var binding: FragmentRandomNumbersListBinding

    private val viewModel: NumberListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRandomNumbersListBinding.inflate(inflater, container, false)

        //Setting up adapter and layout manager
        val adapter = NumberListAdapter()
        binding.rvNumberList.adapter = adapter
        binding.rvNumberList.layoutManager = LinearLayoutManager(requireContext())



        viewModel.getNumbers().observeForever {
            adapter.submitList(it)
        }

        return binding.root
    }


}