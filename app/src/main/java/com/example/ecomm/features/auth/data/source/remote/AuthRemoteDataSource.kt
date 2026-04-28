package com.example.ecomm.features.auth.data.source.remote

import android.util.Log
import com.example.ecomm.core.constants.FirebaseConstants
import com.example.ecomm.core.error.AuthException
import com.example.ecomm.core.error.ServerException
import com.example.ecomm.features.auth.domain.usecases.LoginParams
import com.example.ecomm.features.auth.domain.usecases.RegisterParams
import com.example.ecomm.features.profile.data.model.UserModel
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.Date

interface AuthRemoteDataSource {
    suspend fun loginUser(params: LoginParams): String
    suspend fun registerUser(params: RegisterParams): String
    suspend fun logoutUser()
}

class AuthRemoteDataSourceImpl(private val firebaseAuth:FirebaseAuth, private val firestore: FirebaseFirestore) : AuthRemoteDataSource{
    override suspend fun loginUser(params: LoginParams): String {
        try {
            val result = firebaseAuth
                .signInWithEmailAndPassword(params.email, params.password)
                .await()
            Log.d("TAG","cachedUserId: ${result.user?.uid ?: " not received" }")
            return result.user?.uid ?: throw AuthException("User not found")
        } catch (e: FirebaseAuthException) {
            throw AuthException(e.message ?: "Auth failed")
        } catch (e: Exception) {
            throw ServerException(e.message ?: "Server error")
        }
    }

    override suspend fun registerUser(params: RegisterParams): String {
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(params.email, params.password).await()
            createUser(
                userCredential = result,
                email = params.email,
                fullName = params.fullName
            )
            return  result.user?.uid ?: throw AuthException("Registration Failed")
         }catch (e: FirebaseAuthException) {
            throw AuthException(e.message ?: "Auth failed")
        } catch (e: Exception) {
            throw ServerException(e.message ?: "Server error")
        }
    }

    override suspend fun logoutUser() {
        try {
            firebaseAuth.signOut()
        } catch (e: Exception) {

        }
    }

    private suspend fun createUser(
        userCredential: AuthResult,
        email: String,
        fullName: String
    ) {
        try {
            val userId = userCredential.user!!.uid
            val userModel = UserModel(
                userId = userId,
                fullName = fullName.lowercase(),
                email = email,
                createdAt = Date()
            )
            firestore
                .collection(FirebaseConstants.USERS_COLLECTION)
                .document(userId)
                .set(userModel.toMap())
                .await()
        } catch (e: Exception) {
            userCredential.user?.delete()?.await()
            throw ServerException(e.message ?: "Failed to create user")
        }
    }
}