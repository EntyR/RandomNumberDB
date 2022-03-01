package com.harman.roomdbapp.app.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.harman.roomdbapp.R
import com.harman.roomdbapp.app.adapters.NumberListAdapter
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import com.harman.roomdbapp.databinding.FragmentRandomNumbersListBinding
import org.koin.android.ext.android.inject

class RandomNumbersList : Fragment() {

    private lateinit var binding: FragmentRandomNumbersListBinding

    private val viewModel: NumberListViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRandomNumbersListBinding.inflate(inflater, container, false)


        //Setting up adapter and layout manager
        val adapter = NumberListAdapter(){
            val fragment = RandomNumberDescription.newInstance(it)
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, fragment)
            transaction.addToBackStack("go_to_description")
            transaction.commit()
        }
        binding.rvNumberList.adapter = adapter
        binding.rvNumberList.layoutManager = LinearLayoutManager(requireContext())


        viewModel.getNumbers().observe(viewLifecycleOwner) {
            // make empty list text notification visible if list if empty
            if(it != null){
                binding.textView.visibility = View.VISIBLE
                binding.rvNumberList.visibility = View.INVISIBLE
            } else {
                binding.textView.visibility = View.INVISIBLE
                binding.rvNumberList.visibility = View.VISIBLE
            }

            adapter.submitList(it)
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment RandomNumberDescription.
         */

        @JvmStatic
        fun newInstance() = RandomNumbersList()
    }



}