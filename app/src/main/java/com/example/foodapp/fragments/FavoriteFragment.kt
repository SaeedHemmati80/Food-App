package com.example.foodapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapp.activities.MainActivity
import com.example.foodapp.adapter.FavoriteAndMealAdapter
import com.example.foodapp.databinding.FragmentFavBinding
import com.example.foodapp.viewmodel.HomeViewModel

class FavoriteFragment : Fragment() {

    lateinit var binding: FragmentFavBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteAndMealAdapter: FavoriteAndMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavBinding.inflate(inflater)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initRecyclerViewFavorite()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            @SuppressLint("ShowToast")
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoriteAndMealAdapter.differ.currentList[position])

//                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG)
//                    .setAction("Undo",
//                        View.OnClickListener {
//                            viewModel.insertMeal(favoriteAdapter.differ.currentList[position])
//                        }).show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorite)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerViewFavorite() {
        favoriteAndMealAdapter = FavoriteAndMealAdapter()
        binding.rvFavorite.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoriteAndMealAdapter

        }
    }

    private fun observeFavorites() {
        viewModel.observerFavItemLiveData().observe(requireActivity(), Observer {meals->
            favoriteAndMealAdapter.differ.submitList(meals)

        })
    }

}