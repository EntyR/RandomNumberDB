package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.FragmentGravityChartBinding


class GravityChart : Fragment() {


    lateinit var binding: FragmentGravityChartBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGravityChartBinding.inflate(inflater, container, false)

        binding.btBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btStartRecording.setOnClickListener {
            binding.btStartRecording
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = GravityChart()
    }
}