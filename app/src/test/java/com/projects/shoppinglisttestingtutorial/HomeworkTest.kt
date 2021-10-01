package com.projects.shoppinglisttestingtutorial

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class HomeworkTest{
    /**
     * Returns the n-th fibonacci number
     * They are defined like this:
     * fib(0)=0
     * fib(1)=1
     * fib(2)=fib(0)+fib(1)=1
     * fib(3)=fib(1)+fib(2)=2
     * fib(4)=fib(2)+fib(3)=3
     * fib(5)=5
     * fib(6)=8
     * fib(7)=13
     * fib(n)=fib(n-2)+fib(n-1)
     * */
    @Test
    fun `Fibonacci with input 0 returns 0`(){
        val result=Homework.fib(0)
        assertThat(result, `is`(0))
    }

    @Test
    fun `Fibonacci with input 1 returns 1`(){
        val result=Homework.fib(1)
        assertThat(result, `is`(1))
    }
    @Test
    fun `Fibonacci with input 2 returns 1`(){
        val result=Homework.fib(2)
        assertThat(result, `is`(1))
    }
    @Test
    fun `Fibonacci with input 5 returns 5`(){
        val result=Homework.fib(5)
        assertThat(result, `is`(5))
    }
    @Test
    fun `Fibonacci with 7 zero returns 13`(){
        val result=Homework.fib(7)
        assertThat(result, `is`(13))
    }


    /**
     * Checks if the braces are set correctly
     * e.g."(a+b))" should return false
     * */
    @Test
    fun `Braces set correctly returns true`(){
        val result=Homework.checkBraces("(a=b)")
        assertThat(result,`is`(true))
    }
    @Test
    fun `Braces set incorrectly returns false`(){
        val result=Homework.checkBraces("((a=b)")
        assertThat(result,`is`(false))
    }
}