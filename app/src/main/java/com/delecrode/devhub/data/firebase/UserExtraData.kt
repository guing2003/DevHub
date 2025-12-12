package com.delecrode.devhub.data.firebase

import com.delecrode.devhub.domain.model.RegisterUser
import com.google.firebase.firestore.FirebaseFirestore


class UserExtraData(
    private val firestore: FirebaseFirestore
) {

    suspend fun saveUserData(uid: String, user: RegisterUser) {
        firestore.collection("users")
            .document(uid)
            .set(user)
    }
}
