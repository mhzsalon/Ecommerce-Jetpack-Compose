package com.example.ecomm.features.profile.data.model

import com.example.ecomm.features.profile.domain.entities.UserEntity
import com.google.firebase.Timestamp
import java.util.Date

data class UserModel(
    val userId: String = "",
    val fullName: String = "",
    val email: String = "",
    val createdAt: Date = Date()
) {
    // fromJson — Firestore document to UserModel
    companion object {
        fun fromMap(map: Map<String, Any>): UserModel = UserModel(
            userId    = map["userId"] as? String ?: "",
            fullName  = map["fullName"] as? String ?: "",
            email     = map["email"] as? String ?: "",
            createdAt = (map["createdAt"] as? Timestamp)?.toDate() ?: Date()
        )

        // fromEntity
        fun fromEntity(entity: UserEntity): UserModel = UserModel(
            userId    = entity.userId,
            fullName  = entity.fullName,
            email     = entity.email,
            createdAt = entity.createdAt
        )
    }

    // toJson — UserModel to Firestore document
    fun toMap(): Map<String, Any> = mapOf(
        "userId"    to userId,
        "fullName"  to fullName,
        "email"     to email,
        "createdAt" to createdAt
    )

    // toEntity
    fun toEntity(): UserEntity = UserEntity(
        userId    = userId,
        fullName  = fullName,
        email     = email,
        createdAt = createdAt
    )
}