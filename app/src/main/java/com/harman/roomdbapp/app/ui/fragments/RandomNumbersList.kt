package com.harman.roomdbapp.app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.harman.roomdbapp.app.R
import com.harman.roomdbapp.app.adapters.NumberListAdapter
import com.harman.roomdbapp.app.databinding.FragmentRandomNumbersListBinding
import com.harman.roomdbapp.app.ui.viewmodel.NumberListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomNumbersList : Fragment() {

    private lateinit var binding: FragmentRandomNumbersListBinding

    private val viewModel: NumberListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRandomNumbersListBinding.inflate(inflater, container, false)

        // Setting up adapter and layout manager
        val adapter = NumberListAdapter(requireContext()) {
            val fragment = RandomNumberDescription.newInstance(it)
            navigateTo(fragment, "go_to_description")

        }
        binding.rvNumberList.adapter = adapter
        binding.rvNumberList.layoutManager = LinearLayoutManager(requireContext())

        binding.fabNewItem.setOnClickListener {
            val fragment = RandomNumberAddItem.newInstance()
            navigateTo(fragment, "go_to_new_number_creation")
        }

        viewModel.getNumbers().observe(viewLifecycleOwner) {
            // make empty list text notification visible if list if empty
            if (it.isNullOrEmpty()) {
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

    private fun navigateTo(fragmentToNavigate: Fragment, transactionName: String){
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragmentToNavigate)
        transaction.addToBackStack(transactionName)
        transaction.commit()
    }

}

