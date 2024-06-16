package com.example.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activities.CategoryMealsActivity
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.activities.MealActivity
import com.example.foodapp.adapter.CategoryAdapter
import com.example.foodapp.adapter.PopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.models.Meal
import com.example.foodapp.models.MealsByCategory
import com.example.foodapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init
        viewModel = (activity as MainActivity).viewModel
        popularAdapter = PopularAdapter()

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init recyclerview
        initRecyclerViewPopularItem()


        // Random Meal
        viewModel.getRandomMeal()
        observeRandomMealLiveData()
        onRandomMealClick()

        // Popular item
        viewModel.getPopularItem()
        observePopularItemLiveData()
        onPopularItemClick()

        // Category
        viewModel.getCategories()
        observeCategoryLiveData()
        initRecyclerViewCategory()
        onCategoryMealClick()

        // Navigate to search fragment (onClick search btn)
        onSearchIconClick()



    }

    private fun onSearchIconClick(){
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


    private fun initRecyclerViewPopularItem() {
        binding.apply {
            rvMealPopular.layoutManager =
                LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            rvMealPopular.adapter = popularAdapter
        }
    }

    private fun initRecyclerViewCategory() {
        categoryAdapter = CategoryAdapter()
        binding.apply {
            rvMealCategories.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            rvMealCategories.adapter = categoryAdapter
        }
    }

    private fun observePopularItemLiveData() {
        viewModel.observerPopularItemLiveData().observe(viewLifecycleOwner) {
            popularAdapter.setMealsInAdapter(mealList = it as ArrayList<MealsByCategory>)
        }
    }

    private fun observeRandomMealLiveData() {
        viewModel.observeRandomMealLiveData().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }

    private fun observeCategoryLiveData() {
        viewModel.observerCategory().observe(viewLifecycleOwner, Observer { categories ->
            categoryAdapter.setCategoryInAdapter(categoryList = categories)

        })
    }

    private fun onRandomMealClick() {
        binding.cardMeal.setOnClickListener {
            try{
                val intent = Intent(activity, MealActivity::class.java)
                intent.putExtra("MealId", randomMeal.idMeal)
                intent.putExtra("MealName", randomMeal.strMeal)
                intent.putExtra("MealThumb", randomMeal.strMealThumb)
                startActivity(intent)
            }catch (e:Exception){
                Toast.makeText(context,"loading data...",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onCategoryMealClick(){
        categoryAdapter.onItemClickInAdapter = {category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra("NameCategory", category.strCategory)
            startActivity(intent)
        }

    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClickInAdapter = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra("MealId", it.idMeal)
            intent.putExtra("MealName", it.strMeal)
            intent.putExtra("MealThumb", it.strMealThumb)
            startActivity(intent)
        }
    }
}