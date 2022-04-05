package com.harman.roomdbapp.app.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.ScatterChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.ScatterData
import com.github.mikephil.charting.data.ScatterDataSet
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.FragmentGravityChartBinding
import com.harman.roomdbapp.app.other.PointValueFormatter
import com.harman.roomdbapp.app.other.RecordingState
import com.harman.roomdbapp.app.other.XValueFormatter
import com.harman.roomdbapp.app.other.YValueFormatter
import com.harman.roomdbapp.app.other.addSeconds
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
                    binding.btStartRecording.text = getString(R.string.stop_recording)
                    binding.btStartRecording.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.add_new_number_shape_black,
                            null
                        )

                    viewModel.getSensorData().observe(viewLifecycleOwner) { list ->

                        if (list.isEmpty()) viewModel.isNewSensorRecord = true
                    }
                }
                RecordingState.Stopped -> {
                    viewModel.stopRecording()
                    binding.btStartRecording.text = getString(R.string.start_recording)
                    binding.btStartRecording.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.add_new_number_shape,
                            null
                        )
                }
                else -> Unit
            }
        }
        binding.chScatterChart.xAxis.apply {

            setDrawGridLines(false)
            axisLineColor = Color.BLACK
            binding.chScatterChart.xAxis.setLabelCount(4, true)

            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = XValueFormatter()
            axisLineWidth = 2f
        }
        binding.chScatterChart.axisLeft.apply {
            valueFormatter = YValueFormatter()
            setDrawGridLines(false)
            axisMinimum = 0f
            axisMaximum = 30f
            axisLineColor = Color.BLACK
            axisLineWidth = 2f
        }
        binding.chScatterChart.isAutoScaleMinMaxEnabled = true
        binding.chScatterChart.axisRight.isEnabled = false
        binding.chScatterChart.description.isEnabled = false
        binding.chScatterChart.legend.isEnabled = false

        binding.chScatterChart.post {
            val layoutParm = binding.chScatterChart.layoutParams as LinearLayout.LayoutParams
            layoutParm.height = (binding.chScatterChart.width / 1.2).toInt()
            binding.chScatterChart.layoutParams = layoutParm
        }

        viewModel.getSensorData().observe(viewLifecycleOwner) { list ->

            val dataset = ScatterDataSet(list, "Gravity fluctuation")
            dataset.color = R.color.black
            dataset.setScatterShape(ScatterChart.ScatterShape.CIRCLE)
            dataset.scatterShapeSize = 20f
            dataset.color = Color.BLACK

            val data = ScatterData(dataset)
            data.setValueFormatter(PointValueFormatter())

            if (list.isNotEmpty()) {

                val maxValue =
                    addSeconds(
                        (((10 - list.size) * 2) + 2).toLong(),
                        list.last().x.toLong()
                    )

                val minValue = addSeconds(-2L, list.first().x.toLong())

                binding.chScatterChart.data = data
                binding.chScatterChart.xAxis.axisMaximum = maxValue.toFloat()
                binding.chScatterChart.xAxis.axisMinimum = minValue.toFloat()
                binding.chScatterChart.invalidate()
            }
        }

        viewModel.getRecordingState()

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() = GravityChart()
    }
}
