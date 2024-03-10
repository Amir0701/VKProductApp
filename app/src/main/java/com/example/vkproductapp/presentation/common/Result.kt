package com.example.vkproductapp.presentation.common

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Result<T>(data)
    class Error<T>(message: String): Result<T>(message = message)
    class NoInternetConnection<T>(): Result<T>()
    class Loading<T>(): Result<T>()
}