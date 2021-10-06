package com.projects.shoppinglisttestingtutorial.repositories

import androidx.lifecycle.LiveData
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingDao
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingItem
import com.projects.shoppinglisttestingtutorial.data.remote.PixabyAPI
import com.projects.shoppinglisttestingtutorial.data.remote.responses.ImageResponse
import com.projects.shoppinglisttestingtutorial.other.CustomResult
import com.projects.shoppinglisttestingtutorial.other.CustomResult.Success
import com.projects.shoppinglisttestingtutorial.other.CustomResult.Error
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabyAPI: PixabyAPI
) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem = shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem = shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): CustomResult<ImageResponse> {
        return try {
            val response=pixabyAPI.searchForImage(imageQuery)
            if (response.isSuccessful)
                response.body()?.let {
                    return@let CustomResult.Success(it)
                }?:Error(Exception("Unknown error"))
            else
                Error(Exception("Unknown error"))

        } catch (e:Exception){
            Error(e)
        }
    }
}