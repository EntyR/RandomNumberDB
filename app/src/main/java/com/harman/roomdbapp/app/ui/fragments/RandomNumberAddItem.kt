package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.harman.roomdbapp.app.R

private const val ARG_PARAM1 = "param1"


class RandomNumberAddItem : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_number_add_item, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RandomNumberAddItem()
    }
}