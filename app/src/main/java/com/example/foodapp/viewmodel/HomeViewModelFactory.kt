package com.example.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.db.MealDataBase

class HomeViewModelFactory(private val mealDataBase: MealDataBase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDataBase) as T
    }
}