package com.delecrode.devhub.data.remote.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseAuth(
    private val auth: FirebaseAuth
) {

    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return suspendCoroutine { cont ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        cont.resume(task.result?.user)
                    } else {
                        cont.resumeWithException(
                            task.exception ?: Exception("Erro desconhecido ao fazer login")
                        )
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
                        cont.resumeWithException(
                            task.exception ?: Exception("Erro desconhecido ao cadastrar")
                        )
                    }
                }
        }
    }

    fun signOut() {
        auth.signOut()
    }
}