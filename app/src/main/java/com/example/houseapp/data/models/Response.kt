package com.example.houseapp.data.models

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Failure(val ex: Exception) : Response<Nothing>()
    //object Loading : Response<Nothing>()

    companion object {
        inline fun <T> to(f: () -> T): Response<T> = try {
            Success(f())
        } catch (ex: Exception) {
            Failure(ex)
        }
    }
}