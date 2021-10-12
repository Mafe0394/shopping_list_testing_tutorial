package com.projects.shoppinglisttestingtutorial.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.projects.shoppinglisttestingtutorial.R
import com.projects.shoppinglisttestingtutorial.adapters.ImageAdapter
import com.projects.shoppinglisttestingtutorial.getOrAwaitValue
import com.projects.shoppinglisttestingtutorial.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject


@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule=InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    private lateinit var testViewModel:ShoppingViewModel
    private lateinit var navController: NavController

    @Before
    fun setup() {
        hiltRule.inject()
        navController = mock(NavController::class.java)
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl() {
        val imageUrl="testUrl"
        launchFragmentInHiltContainer<ImagePickFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images= listOf(imageUrl)
            testViewModel=viewModel
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        verify(navController).popBackStack()

        val curImageUrl=testViewModel.curImageUrl.getOrAwaitValue()

        assertThat(curImageUrl,`is`(imageUrl))
    }
}