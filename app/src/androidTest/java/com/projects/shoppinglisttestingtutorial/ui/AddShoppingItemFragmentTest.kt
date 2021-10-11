package com.projects.shoppinglisttestingtutorial.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.projects.shoppinglisttestingtutorial.R
import com.projects.shoppinglisttestingtutorial.getOrAwaitValue
import com.projects.shoppinglisttestingtutorial.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest{

    @get:Rule
    val hiltRule=HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule=InstantTaskExecutorRule()

    private lateinit var navController: NavController

    @Before
    fun setup(){
        hiltRule.inject()
        navController= mock(NavController::class.java)
    }

    @Test
    fun pressBackButton_popBackStack(){

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun clickOnImageView_navigateToImagePickFragment(){

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    @Test
    fun pressBackButton_setCurImageUrlToEmptyString(){
        var testViewModel:ShoppingViewModel?=null

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(),navController)
            testViewModel=this.viewModel
            this.viewModel.setCurImageUrl("imageUrl")
        }

        pressBack()

        val curImageUrl=testViewModel?.curImageUrl?.getOrAwaitValue()

        assertThat(curImageUrl,`is`(""))
    }
}