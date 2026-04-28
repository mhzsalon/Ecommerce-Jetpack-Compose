package com.example.ecomm.features.profile.domain.entities

import java.util.Date

data class UserEntity(
    val userId: String,
    val fullName: String,
    val email: String,
    val createdAt: Date
){
    fun copyWith(
        userId: String = this.userId,
        fullName: String = this.fullName,
        email: String = this.email,
        createdAt: Date = this.createdAt
    ) = copy(
        userId   = userId,
        fullName = fullName,
        email    = email,
        createdAt = createdAt
    )
}