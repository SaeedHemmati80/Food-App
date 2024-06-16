package com.example.foodapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.db.MealDataBase
import com.example.foodapp.models.Meal
import com.example.foodapp.viewmodel.MealViewModel
import com.example.foodapp.viewmodel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealName: String
    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youTubeLink: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent
        getMealInfoFromIntent()
        setMealInfoFromIntent()

        // Load data
        loadingCase()

        // ViewModel
        val mealDataBase = MealDataBase.getInstance(this)
        val factory = MealViewModelFactory(mealDataBase)
        mealMvvm = ViewModelProvider(this, factory)[MealViewModel::class.java]
        mealMvvm.getMealDetail(mealId)
        observerMealDetailsLiveData()

        // Set onClickListener for image youtube
        onYouTubeImageClick()
        onFavoriteClick()


    }

    // Fav btn on click
    private fun onFavoriteClick(){
        binding.fabFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Add To Favorite", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private var mealToSave: Meal? = null
    @SuppressLint("SetTextI18n")
    private fun observerMealDetailsLiveData(){
        mealMvvm.observerMealDetailsLiveData().observe(this, Observer {
            // Response data
            onResponseCase()

            val meal = it
            mealToSave = meal
            binding.tvCategory.text = "Category: ${meal.strCategory}"
            binding.tvArea.text = "Area: ${meal.strArea}"
            binding.tvInstructionsSteps.text = meal.strInstructions

            youTubeLink = meal.strYoutube.toString()
        })
    }

    private fun getMealInfoFromIntent(){
        val intent = intent
        mealId = intent.getStringExtra("MealId")!!
        mealName = intent.getStringExtra("MealName")!!
        mealThumb = intent.getStringExtra("MealThumb")!!
    }
    private fun setMealInfoFromIntent(){
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetails)

        binding.collapsingToolbar.title = mealName
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.fabFav.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.fabFav.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

    private fun onYouTubeImageClick(){
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youTubeLink))
            startActivity(intent)
        }
    }


}