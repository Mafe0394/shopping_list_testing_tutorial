package com.projects.shoppinglisttestingtutorial.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.projects.shoppinglisttestingtutorial.R

class AddShoppingItemFragment:Fragment(R.layout.fragment_add_shopping_item) {
    private val viewModel:ShoppingViewModel by activityViewModels()

}