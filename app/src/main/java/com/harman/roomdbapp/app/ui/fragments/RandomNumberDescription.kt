package com.harman.roomdbapp.app.ui.fragments

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.FragmentRandomNumberDescriptionBinding
import com.harman.roomdbapp.app.other.MathUtils
import com.harman.roomdbapp.app.other.NUMBER_VALUE


class RandomNumberDescription : Fragment() {

    private var numberValue: Int = 0

    private lateinit var binding: FragmentRandomNumberDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            numberValue = it.getInt(NUMBER_VALUE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomNumberDescriptionBinding.inflate(inflater, container, false)

        val numberIsEven = MathUtils.isNumberEven(numberValue)

        binding.ivShareBtn.setOnClickListener {
            val bitmap = getBitMapFromView(it)
            Log.e("TAG", "onCreateView: ${bitmap != null}")
        }

        binding.tvNumberValue.text = resources.getString(R.string.number, numberValue)
        binding.tvNumberDescription.text =
            if (numberIsEven)
                resources.getString(R.string.number_is_even)
            else resources.getString(R.string.number_not_even)

        binding.btBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param numValue number to be displayed.
         * @return A new instance of fragment RandomNumberDescription.
         */

        @JvmStatic
        fun newInstance(numValue: Int) =
            RandomNumberDescription().apply {
                arguments = Bundle().apply {
                    putInt(NUMBER_VALUE, numValue)
                }
            }
    }

    fun getBitMapFromView(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

}
