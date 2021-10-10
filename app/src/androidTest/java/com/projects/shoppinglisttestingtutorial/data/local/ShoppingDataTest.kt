package com.projects.shoppinglisttestingtutorial.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.projects.shoppinglisttestingtutorial.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDataTest {

    @get:Rule
    val hiltRule=HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule=InstantTaskExecutorRule()

    @Inject @Named("test_db") lateinit var database: ShoppingItemDatabase
    private lateinit var  dao:ShoppingDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao=database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun insertShoppingItem()= runBlockingTest {
        val shoppingItem=ShoppingItem(
            name = "Gato",
            amount = 20,
            price = 34f,
            imageUrl = "algo",
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItems=dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems.contains(shoppingItem), `is`(true))
    }

    @Test
    fun deleteShoppingItem()= runBlockingTest{
        val shoppingItem=ShoppingItem(
            name = "Gato",
            amount = 20,
            price = 34f,
            imageUrl = "algo",
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)

        val allShoppingItems=dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems.contains(shoppingItem), `is`(true))

        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems1=dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems1.contains(shoppingItem), `is`(false))

    }

    @Test
    fun observeTotalPriceSum()= runBlockingTest {
        val shoppingItem=ShoppingItem(
            name = "Gato1",
            amount = 2,
            price = 10f,
            imageUrl = "algo",
            id = 1
        )
        val shoppingItem2=ShoppingItem(
            name = "Gato2",
            amount = 3,
            price = 5f,
            imageUrl = "algo",
            id = 2
        )
        val shoppingItem3=ShoppingItem(
            name = "Gato3",
            amount = 4,
            price = 8f,
            imageUrl = "algo",
            id = 3
        )

        dao.insertShoppingItem(shoppingItem)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val  totalPriceSum=dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum,`is`(2*10f+3*5f+4*8f))
    }

}