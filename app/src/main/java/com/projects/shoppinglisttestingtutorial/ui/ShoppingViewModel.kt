package com.projects.shoppinglisttestingtutorial.ui

import androidx.lifecycle.*
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingItem
import com.projects.shoppinglisttestingtutorial.data.remote.responses.ImageResponse
import com.projects.shoppinglisttestingtutorial.other.Constants
import com.projects.shoppinglisttestingtutorial.other.CustomResult
import com.projects.shoppinglisttestingtutorial.other.Event
import com.projects.shoppinglisttestingtutorial.other.Resource
import com.projects.shoppinglisttestingtutorial.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
) : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<CustomResult<ImageResponse>>>()
    val images: LiveData<Event<CustomResult<ImageResponse>>>
        get() = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String>
        get() = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<CustomResult<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<CustomResult<ShoppingItem>>>
        get() = _insertShoppingItemStatus

    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
        if (name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()) {
            _insertShoppingItemStatus.postValue(Event(CustomResult.Error(Exception("The Fields must not be empty"))))
            return
        }
        if (name.length > Constants.MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(CustomResult.Error(Exception("Max 20 characters for the name"))))
            return
        }
        if (priceString.length > Constants.MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(CustomResult.Error(Exception("No more than 10 digits"))))
            return
        }
        val amount = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(Event(CustomResult.Error(e)))
            return
        }
        val shoppingItem =
            ShoppingItem(name, amount, priceString.toFloat(), _curImageUrl.value ?: "")
        insertShoppingItemIntoDb(shoppingItem)
        // we reset the current image so is empty when we comeback to the fragment
        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(Event(CustomResult.Success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty())
            return
        _images.value= Event(CustomResult.Loading)
        viewModelScope.launch {
            val response=repository.searchForImage(imageQuery)
            _images.value=Event(response)
        }
    }
}