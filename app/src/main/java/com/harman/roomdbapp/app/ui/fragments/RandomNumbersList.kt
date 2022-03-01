package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.harman.roomdbapp.R
import com.harman.roomdbapp.databinding.FragmentRandomNumbersListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RandomNumbersList.newInstance] factory method to
 * create an instance of this fragment.
 */
class RandomNumbersList : Fragment() {

    private lateinit var binding: FragmentRandomNumbersListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRandomNumbersListBinding.inflate(inflater, container, false)



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RandomNumbersList.
         */

        fun newInstance() = RandomNumbersList()

    }
}