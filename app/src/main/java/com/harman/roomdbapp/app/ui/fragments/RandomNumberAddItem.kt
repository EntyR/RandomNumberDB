package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.harman.roomdbapp.app.databinding.FragmentRandomNumberAddItemBinding
import com.harman.roomdbapp.app.other.AddNumberSate
import com.harman.roomdbapp.app.other.MathUtils.generateRandomNumber
import com.harman.roomdbapp.app.ui.viewmodel.AddNumberViewModel
import com.harman.roomdbapp.domain.model.RandomNumber
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomNumberAddItem : Fragment() {

    private lateinit var binding: FragmentRandomNumberAddItemBinding

    private val viewModel: AddNumberViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRandomNumberAddItemBinding.inflate(inflater, container, false)

        binding.etEnterNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    viewModel.switchState()
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    viewModel.switchState()
                }
            }

            override fun afterTextChanged(p0: Editable?) = Unit
        })

        viewModel.isTextAdded.observe(viewLifecycleOwner) {
            when (it) {
                AddNumberSate.AddCustom -> {
                    binding.btAddNewNumber.visibility = View.GONE
                    binding.btLargeAddNumber.visibility = View.VISIBLE
                    binding.btOr.visibility = View.GONE
                }
                AddNumberSate.AddRandom -> {
                    binding.btAddNewNumber.visibility = View.VISIBLE
                    binding.btLargeAddNumber.visibility = View.GONE
                    binding.btOr.visibility = View.VISIBLE
                }
                AddNumberSate.Done -> {
                    parentFragmentManager.popBackStack()
                }
                null -> Unit
            }
        }
        binding.btAddNewNumber.setOnClickListener {
            viewModel.addNumber(
                RandomNumber(
                    generateRandomNumber()
                )
            )
        }

        binding.btLargeAddNumber.setOnClickListener {
            if (binding.etEnterNumber.text.isNotBlank()) {
                viewModel.addNumber(
                    RandomNumber(
                        binding.etEnterNumber.text.toString().toInt()
                    )
                )
            }
        }

        binding.btBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RandomNumberAddItem()
    }
}
