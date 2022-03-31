package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.FragmentGravityChartBinding
import com.harman.roomdbapp.app.other.RecordingState
import com.harman.roomdbapp.app.ui.viewmodel.GravityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GravityChart : Fragment() {

    lateinit var binding: FragmentGravityChartBinding

    private val viewModel: GravityViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGravityChartBinding.inflate(inflater, container, false)

        binding.btBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btStartRecording.setOnClickListener {
            viewModel.switchRecordState()
        }

        viewModel.recordingState.observe(viewLifecycleOwner) {
            when (it) {
                RecordingState.Started -> {
                    viewModel.startRecording()
                    binding.btStartRecording.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.add_new_number_shape_black, null)

                    viewModel.getSensorData().observe(viewLifecycleOwner) { list ->
                        if (list.size >= 10) viewModel.switchRecordState()
                    }
                }
                RecordingState.Stopped -> {
                    viewModel.stopRecording()
                    binding.btStartRecording.background =
                        ResourcesCompat.getDrawable(resources, R.drawable.add_new_number_shape, null)
                }
                else -> Unit
            }
        }

        viewModel.getSensorData().observe(viewLifecycleOwner) { list ->

            val entry = listOf(Entry(1f, 2f),
            Entry(2f,3f)
            )

            //TODO populate with real value

            val dataset = ScatterDataSet(entry, "Gravity fluctuation")
            dataset.color = R.color.black

            val data = ScatterData(dataset)

            binding.chScatterChart.data = data
            binding.chScatterChart.invalidate()
            binding.chScatterChart.xAxis.setDrawGridLines(false);
            binding.chScatterChart.axisLeft.setDrawGridLines(false);
            binding.chScatterChart.axisRight.setDrawGridLines(false);
        }

        viewModel.getRecordingState()

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = GravityChart()
    }
}
