package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateMargins
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.databinding.FragmentRandomNumberAddItemBinding
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RandomNumberAddItem : Fragment() {

private  lateinit var binding: FragmentRandomNumberAddItemBinding

    private val viewModel: NumberListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRandomNumberAddItemBinding.inflate(inflater, container, false)
        val layoutParam = ConstraintLayout.LayoutParams(binding.btAddNewNumber.layoutParams as ConstraintLayout.LayoutParams)
        binding.etEnterNumber.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()){
                    val changedParams  = ConstraintLayout.LayoutParams(layoutParam)
                    val horMargin = requireContext().resources.getDimensionPixelSize(R.dimen.add_number_btn_margin)
                    changedParams.topToBottom = ConstraintLayout.LayoutParams.UNSET
                    changedParams.startToStart = binding.root.id
                    changedParams.endToEnd = binding.root.id
                    changedParams.bottomToBottom = binding.root.id
                    changedParams.setMargins(horMargin,0,horMargin,140)
                    changedParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    changedParams.width = 0
                    binding.btAddNewNumber.layoutParams = changedParams
                    binding.btAddNewNumber.textSize = 21f
                    binding.btAddNewNumber.text = getString(R.string.add_number)
                    binding.btAddNewNumber.minHeight = 190
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int)  {
                if (p0.isNullOrBlank()){
                    binding.btAddNewNumber.layoutParams = layoutParam
                    binding.btAddNewNumber.textSize = 16f
                    getString(R.string.add_random)
                    binding.btAddNewNumber.minHeight = 42
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