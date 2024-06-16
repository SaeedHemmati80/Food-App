package com.example.foodapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.adapter.CategoryMealsAdapter
import com.example.foodapp.databinding.ActivityCategoryMealsBinding
import com.example.foodapp.viewmodel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var mvvmMealsByCategory: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerViewCategoryMeals()
        mvvmMealsByCategory = ViewModelProvider(this)[CategoryMealsViewModel::class.java]

        mvvmMealsByCategory.getMealsByCategory(intent.getStringExtra("NameCategory")!!)
        mvvmMealsByCategory.observerMealsLiveData().observe(this, Observer {
            binding.tvCountCategory.text = it.size.toString()
            categoryMealsAdapter.setMealsList(it)
        })

    }

    private fun initRecyclerViewCategoryMeals(){
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvCategoryMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}