package com.delecrode.devhub.domain.session

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SessionViewModel(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        auth.addAuthStateListener {
            _isLoggedIn.value = it.currentUser != null
        }
    }

    fun signOut() {
        auth.signOut()
    }
}