package com.projects.shoppinglisttestingtutorial

import org.hamcrest.CoreMatchers.`is`
import org.junit.Test
import org.hamcrest.MatcherAssert.assertThat

class RegistrationUtilTest{
    @Test
    fun `Empty userName returns false`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "",
            "12345678",
            "12345678"
        )

        assertThat(result, `is`(false))
    }

    @Test
    fun `Valid userName and correctly repeated password returns true`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "Juanito",
            "12345678",
            "12345678"
        )

        assertThat(result, `is`(true))
    }

    @Test
    fun `Username already exists returns false`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "Carl",
            "12345678",
            "12345678"
        )

        assertThat(result, `is`(false))
    }

    @Test
    fun `Empty password returns false`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "Juanito",
            "",
            ""
        )

        assertThat(result, `is`(false))
    }

    @Test
    fun `Password repeated incorrectly returns false`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "Juanito",
            "12345678",
            "97654321"
        )

        assertThat(result, `is`(false))
    }

    @Test
    fun `Password with less than 4 digits`(){
        val result=RegistrationUtil.validateRegistrationInput(
            "Juanito",
            "123",
            "123"
        )

        assertThat(result, `is`(false))
    }
}