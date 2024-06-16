package com.example.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.db.MealDataBase
import com.example.foodapp.models.Meal
import com.example.foodapp.models.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(val mealDataBase: MealDataBase): ViewModel() {

    private val mealDetailsMutableLiveData = MutableLiveData<Meal>()

    // Meal details
    fun getMealDetail(id: String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body() != null){
                    mealDetailsMutableLiveData.value = response.body()!!.meals[0]
                }
                else
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("MealActivity", t.message.toString())
            }
        })
    }

    fun insertMeal(meal: Meal) = viewModelScope.launch {
        mealDataBase.mealDao().upSet(meal)
    }

    fun deleteMeal(meal: Meal) = viewModelScope.launch {
        mealDataBase.mealDao().delete(meal)
    }


    fun observerMealDetailsLiveData(): LiveData<Meal>{
        return mealDetailsMutableLiveData
    }



}