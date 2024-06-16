package com.example.foodapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.FavoriteAndMealAdapter
import com.example.foodapp.databinding.FragmentSearchBinding
import com.example.foodapp.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding
    lateinit var viewModel: HomeViewModel
    lateinit var searchMealAdapter: FavoriteAndMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewSearch()
        binding.imgSearchArrow.setOnClickListener { searchMeal() }
        observeSearchMealLiveData()

        // Auto display meal in search
        var searchJob: Job? = null
        binding.etSearch.addTextChangedListener{
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMeal(searchQuery = it.toString())

            }
        }

    }

    private fun observeSearchMealLiveData() {
        viewModel.observerSearchMealsLiveData().observe(viewLifecycleOwner, Observer {
            searchMealAdapter.differ.submitList(it)
        })
    }

    private fun searchMeal() {
        val searchQuery = binding.etSearch.text.toString()
        if (searchQuery.isNotEmpty())
            viewModel.searchMeal(searchQuery)
    }

    private fun initRecyclerViewSearch(){
        searchMealAdapter = FavoriteAndMealAdapter()
        binding.rvSearchMeal.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = searchMealAdapter
        }
    }
}