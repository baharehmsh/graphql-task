package com.example.graphqlapp.model

sealed class Either<T> {

    data class Success<T>(val data: T) : Either<T>()

    data class Failure<T>(val exception: Exception) : Either<T>()

}

