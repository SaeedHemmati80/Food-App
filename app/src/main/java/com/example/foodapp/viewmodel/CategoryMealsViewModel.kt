package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.models.MealsByCategory
import com.example.foodapp.models.MealsByCategoryList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel: ViewModel() {
    private val malesLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let {mealsList ->
                    malesLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.i("CategoryMealsViewModel", t.message.toString())
            }

        })
    }

    fun observerMealsLiveData(): LiveData<List<MealsByCategory>>{
        return malesLiveData
    }
}