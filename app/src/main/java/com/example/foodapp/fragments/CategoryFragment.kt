package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.CategoryAdapter
import com.example.foodapp.databinding.FragmentCategoryBinding
import com.example.foodapp.viewmodel.HomeViewModel

class CategoryFragment : Fragment() {

    lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // در مین اکتیویتی تعریف شده و در چندجا استفاده میشود جهت راحتی کار
        viewModel = (activity as MainActivity).viewModel
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewCategories()
        observeCategoryLiveData()
        onCategoryMealClick()
    }

    private fun onCategoryMealClick() {

        categoryAdapter.onItemClickInAdapter = { category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra("NameCategory", category.strCategory)
            startActivity(intent)
        }


    }

    private fun observeCategoryLiveData() {
        viewModel.observerCategory().observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.setCategoryInAdapter(categories)
        })
    }

    private fun initRecyclerViewCategories() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }


}