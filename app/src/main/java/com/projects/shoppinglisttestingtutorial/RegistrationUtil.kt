package com.projects.shoppinglisttestingtutorial

object RegistrationUtil {

    private val existingUsers= listOf("Peter","Carl")

    /**
     * the input is not valid if...
     * ... username/password is empty
     * ... username already taken
     * ... password missmatch
     * ... password contains less than 4 digits
     * */
    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmedPassword:String
    ) : Boolean{
        if (userName.isEmpty()||password.isEmpty())
            return false
        else if(userName in existingUsers)
            return false
        else if (password!=confirmedPassword)
            return false
        else return password.length >= 4
    }
}