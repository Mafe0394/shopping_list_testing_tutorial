package com.projects.shoppinglisttestingtutorial.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.projects.shoppinglisttestingtutorial.R
import com.projects.shoppinglisttestingtutorial.adapters.ImageAdapter
import com.projects.shoppinglisttestingtutorial.other.Constants.GRID_SPAN_COUNT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_image_pick.*
import javax.inject.Inject


class ImagePickFragment @Inject constructor(
    val imageAdapter: ImageAdapter
):Fragment(R.layout.fragment_image_pick) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        setupRecyclerView()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setCurImageUrl(it)
        }
    }

    private fun setupRecyclerView(){
        rvImages.apply {
            adapter=imageAdapter
            layoutManager=GridLayoutManager(requireContext(),GRID_SPAN_COUNT)
        }
    }
}