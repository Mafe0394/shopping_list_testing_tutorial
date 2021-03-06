package com.projects.shoppinglisttestingtutorial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.projects.shoppinglisttestingtutorial.R
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingItem
import kotlinx.android.synthetic.main.item_image.view.ivShoppingImage
import kotlinx.android.synthetic.main.item_shopping.view.*
import javax.inject.Inject

class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>() {
    inner class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>() {
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listShoppingItem: List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder =
        ShoppingItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping,
            parent,
            false))

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val shoppingItem=listShoppingItem[position]
        holder.itemView.apply {
            glide.load(shoppingItem.imageUrl).into(ivShoppingImage)

            tvName.text = shoppingItem.name
            val amountText = "${shoppingItem.amount}x"
            tvShoppingItemAmount.text = amountText
            val priceText = "${shoppingItem.price}???"
            tvShoppingItemPrice.text = priceText
        }
    }

    override fun getItemCount(): Int = listShoppingItem.size
}