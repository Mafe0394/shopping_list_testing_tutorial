package com.projects.shoppinglisttestingtutorial.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projects.shoppinglisttestingtutorial.R
import kotlinx.android.synthetic.main.fragment_shopping.*

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private val viewModel: ShoppingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(ShoppingFragmentDirections
                .actionShoppingFragmentToAddShoppingItemFragment())
        }
    }
}