package com.projects.shoppinglisttestingtutorial.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.projects.shoppinglisttestingtutorial.R

class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

    private val viewModel:ShoppingViewModel by activityViewModels()
}