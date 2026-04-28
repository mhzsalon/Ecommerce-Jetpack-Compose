package com.example.ecomm.core.error
// Failures
sealed class Failure(open val message: String)

data class CacheFailure(
    override val message: String = "Cache error occurred"
) : Failure(message)

data class NetworkFailure(
    override val message: String = "No internet connection"
) : Failure(message)

data class ServerFailure(
    override val message: String = "Server error"
) : Failure(message)

data class AuthFailure(
    override val message: String
) : Failure(message)