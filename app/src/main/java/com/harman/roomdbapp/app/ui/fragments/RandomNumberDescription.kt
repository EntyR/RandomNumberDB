package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.harman.roomdbapp.R
import com.harman.roomdbapp.app.other.MathUtils
import com.harman.roomdbapp.databinding.FragmentRandomNumberDescriptionBinding
import com.harman.roomdbapp.databinding.FragmentRandomNumbersListBinding


private const val NUMBER_VALUE = "random_number_value"


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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomNumberDescriptionBinding.inflate(inflater, container, false)

        val numberIsEven = MathUtils.isNumberEven(numberValue)

        binding.tvNumberValue.text = numberValue.toString()
        binding.tvNumberDescription.text =
            if (numberIsEven)
                resources.getString(R.string.number_is_even)
            else resources.getString(R.string.number_not_even)

        return binding.root
    }


}