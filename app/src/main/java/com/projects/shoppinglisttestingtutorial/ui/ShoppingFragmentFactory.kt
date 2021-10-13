package com.projects.shoppinglisttestingtutorial.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.projects.shoppinglisttestingtutorial.adapters.ImageAdapter
import com.projects.shoppinglisttestingtutorial.adapters.ShoppingItemAdapter
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingItem
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide:RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            AddShoppingItemFragment::class.java.name->AddShoppingItemFragment(glide)
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            ShoppingFragment::class.java.name->ShoppingFragment(shoppingItemAdapter)
            else -> super.instantiate(classLoader, className)
        }

    }
}