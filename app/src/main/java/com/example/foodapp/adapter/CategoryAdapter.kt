package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.CategoryItemBinding
import com.example.foodapp.models.Category

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categoryList =  ArrayList<Category>()
    lateinit var onItemClickInAdapter: ((Category) -> Unit)
    fun setCategoryInAdapter(categoryList: List<Category>){
        this.categoryList = categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvNameCategory.text = categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClickInAdapter.invoke(categoryList[position])
        }
    }

    inner class ViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)
}
