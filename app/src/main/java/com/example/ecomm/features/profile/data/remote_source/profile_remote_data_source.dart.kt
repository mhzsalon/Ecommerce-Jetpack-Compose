package com.example.ecomm.features.profile.data.remote_source

import android.util.Log
import android.util.Log.e
import com.example.ecomm.core.constants.FirebaseConstants
import com.example.ecomm.core.error.ServerException
import com.example.ecomm.features.profile.data.model.UserModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

interface ProfileRemoteDataSource {
    suspend fun fetchProfile(userId: String): UserModel
    suspend fun updateProfile(user: UserModel)
}


class ProfileRemoteDataSourceImpl(private val firestore: FirebaseFirestore) :
    ProfileRemoteDataSource {
    override suspend fun fetchProfile(userId: String): UserModel {
        try {
            Log.e("TAG", userId)

            //fetch the user doc from firebase users collection
            val result =
                firestore.collection(FirebaseConstants.USERS_COLLECTION).document(userId).get()
                    .await()

//  safe cast with null check
            val data = result.data ?: throw Exception("User not found")

            return UserModel.fromMap(data)
        } catch (e: Exception) {
            Log.e("TAG", e.message ?: "Error fetching profile")
            throw ServerException(e.message ?: "Error fetching profile")
        }
    }

    override suspend fun updateProfile(user: UserModel) {
        try {
            //fetch the user doc from firebase users collection
            val result =
                firestore.collection(FirebaseConstants.USERS_COLLECTION).document(user.userId)
                    .set(user.toMap()).await()
        } catch (e: Exception) {
            throw ServerException(e.message ?: "Error fetching profile")
        }
    }
}