package com.projects.shoppinglisttestingtutorial.ui

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.projects.shoppinglisttestingtutorial.R
import com.projects.shoppinglisttestingtutorial.other.CustomResult
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import javax.inject.Inject

class AddShoppingItemFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_add_shopping_item) {

    val viewModel: ShoppingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()

        btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                etShoppingItemName.text.toString(),
                etShoppingItemAmount.text.toString(),
                etShoppingItemPrice.text.toString()
            )
        }

        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObservers() {
        viewModel.curImageUrl.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(ivShoppingImage)
        })
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, {
            it.getContentIfNotHandled()?.let { results ->
                when (results) {
                    is CustomResult.Success -> {
                        requireActivity().currentFocus?.let { view ->
                            Snackbar.make(
                                view,
                                "Added Shopping Item",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        findNavController().popBackStack()
                    }
                    is CustomResult.Error -> {
                        requireActivity().currentFocus?.let { view ->
                            Snackbar.make(
                                view,
                                results.exception.toString(),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                    is CustomResult.Loading -> {
                        /* NO -OP */
                    }
                }
            }
        })
    }
}