package com.projects.shoppinglisttestingtutorial

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparerTest{

    private lateinit var resourceComparer:ResourceComparer
    private lateinit var context: Context

    @Before
    fun setUp(){
        resourceComparer= ResourceComparer()
        context=ApplicationProvider.getApplicationContext<Context>()
    }

    @After
    fun teardown(){
        // destroy objects
    }

    @Test
    fun isEqual_stringFromResSameAsGivenString_returnsTrue(){
        val result=resourceComparer.isEqual(context,R.string.app_name,"ShoppingList Testing Tutorial")
        assertThat(result,`is`(true))
    }

    @Test
    fun isEqual_stringFromResDifferentSameAsGivenString_returnsFalse(){
        val result=resourceComparer.isEqual(context,R.string.app_name,"algo")
        assertThat(result,`is`(false))
    }
}