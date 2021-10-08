package com.projects.shoppinglisttestingtutorial.ui

import androidx.lifecycle.*
import com.projects.shoppinglisttestingtutorial.data.local.ShoppingItem
import com.projects.shoppinglisttestingtutorial.data.remote.responses.ImageResponse
import com.projects.shoppinglisttestingtutorial.other.CustomResult
import com.projects.shoppinglisttestingtutorial.other.Event
import com.projects.shoppinglisttestingtutorial.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository,
    private val savedStateHandle: SavedStateHandle
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

    fun setCurImageUrl(url:String){
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem)=viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem)=viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name:String,amountString:String,priceString: String){

    }

    fun searchForImage(imageQuery:String){

    }
}