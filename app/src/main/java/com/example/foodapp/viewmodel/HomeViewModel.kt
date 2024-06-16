package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDataBase
import com.example.foodapp.models.Category
import com.example.foodapp.models.CategoryList
import com.example.foodapp.models.Meal
import com.example.foodapp.models.MealList
import com.example.foodapp.models.MealsByCategory
import com.example.foodapp.models.MealsByCategoryList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDataBase: MealDataBase): ViewModel() {

    private val randomMealLiveData = MutableLiveData<Meal>()
    private val popularItemMutableLiveData = MutableLiveData<List<MealsByCategory>>()
    private val categoryMutableLiveData = MutableLiveData<List<Category>>()
    private val favoriteItemMealLiveData = mealDataBase.mealDao().getAllMeals()
    private val searchedMealLiveData = MutableLiveData<List<Meal>>()


    // Get random meal
    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                }
                else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.i("HomeFragment", t.message.toString())
            }

        })
    }

    // Get popular items
    fun getPopularItem(){
        RetrofitInstance.api.getPopularItem("Seafood").enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if(response.body() != null){
                    popularItemMutableLiveData.value = response.body()!!.meals
                }
                else
                    return
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }

        })
    }

    // Get category
    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() != null){
                    categoryMutableLiveData.value = response.body()!!.categories
                }
                else
                    return
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }

        })
    }

    fun searchMeal(searchQuery: String) = RetrofitInstance.api.getSearchMeals(searchQuery).enqueue(
        object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList = response.body()!!.meals
                mealList?.let {
                    searchedMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }

        })



    fun deleteMeal(meal: Meal) = viewModelScope.launch {
        mealDataBase.mealDao().delete(meal)
    }

    // Observer random meal
    fun observeRandomMealLiveData(): LiveData<Meal>{
        return randomMealLiveData
    }

    // Observer popular item
    fun observerPopularItemLiveData(): LiveData<List<MealsByCategory>>{
        return popularItemMutableLiveData
    }

    // Observer category
    fun observerCategory(): LiveData<List<Category>>{
        return categoryMutableLiveData
    }

    // Observer fav item meals
    fun observerFavItemLiveData(): LiveData<List<Meal>>{
        return favoriteItemMealLiveData
    }

    fun observerSearchMealsLiveData(): LiveData<List<Meal>>{
        return searchedMealLiveData
    }

}