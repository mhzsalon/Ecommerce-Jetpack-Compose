package com.example.ecomm.core.error
sealed class AppException(override val message: String) : Exception(message)

class CacheException(
    message: String = "Cache error"
) : AppException(message)

class ServerException(
    message: String = "Server error"
) : AppException(message)

class AuthException(
    message: String
) : AppException(message)