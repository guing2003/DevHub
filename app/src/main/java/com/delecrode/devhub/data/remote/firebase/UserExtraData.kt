package com.delecrode.devhub.data.remote.firebase

import com.delecrode.devhub.domain.model.RegisterUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserExtraData(
    private val firestore: FirebaseFirestore
) {

    fun saveUserData(uid: String, user: RegisterUser) {
        firestore.collection("users")
            .document(uid)
            .set(user)
    }

    suspend fun getUser(uid: String): DocumentSnapshot {
        return firestore.collection("users")
            .document(uid)
            .get()
            .await()
    }
}