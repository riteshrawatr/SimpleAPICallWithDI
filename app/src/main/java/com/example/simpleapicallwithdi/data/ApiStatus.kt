package com.example.simpleapicallwithdi.data

sealed class ApiStatus<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ApiStatus<T>(data)
    class Error<T>(message: String, data: T? = null) : ApiStatus<T>(data, message)
    class Loading<T>(data: T? = null) : ApiStatus<T>(data)
}
