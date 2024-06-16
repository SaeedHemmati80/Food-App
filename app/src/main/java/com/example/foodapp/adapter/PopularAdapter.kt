package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.models.MealsByCategory

class PopularAdapter(): RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private var mealList =  ArrayList<MealsByCategory>()
    lateinit var onItemClickInAdapter: ((MealsByCategory) -> Unit)

    fun setMealsInAdapter(mealList: ArrayList<MealsByCategory>){
        this.mealList = mealList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PopularItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Set image for rv popular items
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        // Clickable for each items popular
        holder.itemView.setOnClickListener {
            onItemClickInAdapter.invoke(mealList[position])
        }
    }
    inner class ViewHolder(var binding: PopularItemBinding): RecyclerView.ViewHolder(binding.root)
}