package com.projects.shoppinglisttestingtutorial.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.projects.shoppinglisttestingtutorial.MainCoroutineRule
import com.projects.shoppinglisttestingtutorial.getOrAwaitValueTest
import com.projects.shoppinglisttestingtutorial.other.Constants
import com.projects.shoppinglisttestingtutorial.other.succeeded
import com.projects.shoppinglisttestingtutorial.repositories.FakeShoppingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest{

    @get:Rule
    var instantTaskExecutableRule=InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule=MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup() {
        viewModel=ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field, returns error`(){
        viewModel.insertShoppingItem("name","","3.0")

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.succeeded,`is`(false))
    }

    @Test
    fun `insert shopping item with too long names, returns error`(){
        val string= buildString {
            for(i in 1..Constants.MAX_NAME_LENGTH+1)
                append(1)
        }
        viewModel.insertShoppingItem(string,"5","3.0")

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.succeeded,`is`(false))
    }

    @Test
    fun `insert shopping item with too long price, returns error`(){
        val string= buildString {
            for(i in 1..Constants.MAX_PRICE_LENGTH+1)
                append(1)
        }
        viewModel.insertShoppingItem("string","5",string)

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.succeeded,`is`(false))
    }

    @Test
    fun `insert shopping item with too high amount, returns error`(){
        viewModel.insertShoppingItem("string","9999999999999999","3.0")

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.succeeded,`is`(false))
    }

    @Test
    fun `insert shopping item with valid input, returns error`(){
        viewModel.insertShoppingItem("string","5","3.0")

        val value=viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.succeeded,`is`(true))
    }
}