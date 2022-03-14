package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.FragmentRandomNumberAddItemBinding
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RandomNumberAddItem : Fragment() {

    private lateinit var binding: FragmentRandomNumberAddItemBinding

    private val viewModel: NumberListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRandomNumberAddItemBinding.inflate(inflater, container, false)
        val layoutParam =
            ConstraintLayout.LayoutParams(binding.btAddNewNumber.layoutParams as ConstraintLayout.LayoutParams)
        binding.etEnterNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    val changedParams = ConstraintLayout.LayoutParams(layoutParam)
                    val horMargin =
                        requireContext().resources.getDimensionPixelSize(R.dimen.add_number_btn_margin)
                    val bottomMargin = requireContext().resources.getDimensionPixelSize(R.dimen.big_btn_btn_margin)
                    val topMargin = requireContext().resources.getDimensionPixelSize(R.dimen.big_btn_top_margin)
                    val width = requireContext().resources.getDimensionPixelSize(R.dimen.big_btn_width)
                    val textSize = requireContext().resources.getDimension(R.dimen.big_btn_text_size)
                    changedParams.topToBottom = ConstraintLayout.LayoutParams.UNSET
                    changedParams.startToStart = binding.root.id
                    changedParams.endToEnd = binding.root.id
                    changedParams.bottomToBottom = binding.root.id
                    changedParams.setMargins(horMargin, topMargin, horMargin, bottomMargin)
                    changedParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    changedParams.width = width
                    binding.btAddNewNumber.layoutParams = changedParams
                    binding.btAddNewNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                    binding.btAddNewNumber.text = getString(R.string.add_number)
                    binding.btAddNewNumber.minHeight = requireContext().resources.getDimensionPixelSize(R.dimen.big_btn_min_height)
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    val textSize = requireContext().resources.getDimension(R.dimen.add_random_text_size)
                    binding.btAddNewNumber.layoutParams = layoutParam
                    binding.btAddNewNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
                    getString(R.string.add_random)
                    binding.btAddNewNumber.minHeight = requireContext().resources.getDimensionPixelSize(R.dimen.add_random_min_height)
                }
            }

            override fun afterTextChanged(p0: Editable?) = Unit
        })




        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RandomNumberAddItem()
    }
}