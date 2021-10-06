package com.projects.shoppinglisttestingtutorial.other


/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class CustomResult<out R> {

    data class Success<out T>(val data: T) : CustomResult<T>()
    data class Error(val exception: Exception) : CustomResult<Nothing>()
    object Loading : CustomResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val CustomResult<*>.succeeded
    get() = this is CustomResult.Success && data != null