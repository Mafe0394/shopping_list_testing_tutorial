package com.projects.shoppinglisttestingtutorial.repositories

import androidx.lifecycle.LiveData
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingItem
import com.projects.shoppinglisttestingtutorial.data.remote.responses.ImageResponse
import com.projects.shoppinglisttestingtutorial.other.CustomResult

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems():LiveData<List<ShoppingItem>>

    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(imageQuery:String):CustomResult<ImageResponse>

}