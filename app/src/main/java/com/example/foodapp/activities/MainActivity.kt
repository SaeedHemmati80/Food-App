package com.example.foodapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.db.MealDataBase
import com.example.foodapp.viewmodel.HomeViewModel
import com.example.foodapp.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDataBase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }
}