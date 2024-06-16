package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealsItemBinding
import com.example.foodapp.models.Meal

class FavoriteAndMealAdapter: RecyclerView.Adapter<FavoriteAndMealAdapter.ViewHolder>() {




    // Best performance, new way, notify to change
    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MealsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide
            .with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeals)

        holder.binding.tvMealsName.text = meal.strMeal
    }

    inner class ViewHolder(val binding: MealsItemBinding): RecyclerView.ViewHolder(binding.root)
}