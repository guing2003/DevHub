package com.delecrode.devhub.data.firebase

import com.google.firebase.auth.FirebaseUser
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import com.google.firebase.auth.FirebaseAuth as GoogleFirebaseAuth

class FirebaseAuth(
    private val auth: GoogleFirebaseAuth
) {

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(task.result?.user)
                    } else {
                        cont.resumeWithException(task.exception ?: Exception("Erro desconhecido ao fazer login"))
                    }
                }
        }
    }

    suspend fun signUp(email: String, password: String): String? {
        return suspendCoroutine { cont ->
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(task.result?.user?.uid)
                    } else {
                        cont.resumeWithException(task.exception ?: Exception("Erro desconhecido ao cadastrar"))
                    }
                }
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
