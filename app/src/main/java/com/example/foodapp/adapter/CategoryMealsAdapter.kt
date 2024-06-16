package com.example.foodapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealsItemBinding
import com.example.foodapp.models.MealsByCategory

class CategoryMealsAdapter: RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {
    private var mealsList = ArrayList<MealsByCategory>()
    @SuppressLint("NotifyDataSetChanged")
    fun setMealsList(mealsList: List<MealsByCategory>){
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MealsItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeals)
        holder.binding.tvMealsName.text = mealsList[position].strMeal
    }

    inner class ViewHolder(var binding: MealsItemBinding): RecyclerView.ViewHolder(binding.root)
}