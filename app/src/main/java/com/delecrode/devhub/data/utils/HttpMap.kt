package com.delecrode.devhub.data.utils

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException


fun mapHttpError(code: Int): String =
    when (code) {
        404 -> "Repositório não encontrado"
        403 -> "Limite da API atingido"
        in 500..599 -> "Servidor indisponível"
        else -> "Erro ao buscar dados"
    }


fun mapAuthError(e: Exception): String =
    when (e) {
        is FirebaseAuthInvalidUserException ->
            "Usuário não encontrado."

        is FirebaseAuthInvalidCredentialsException ->
            "E-mail ou senha inválidos."

        is FirebaseAuthUserCollisionException ->
            "Este e-mail já está em uso."

        is FirebaseAuthWeakPasswordException ->
            "A senha deve ter no mínimo 6 caracteres."

        else ->
            "Erro ao realizar login. Tente novamente."
    }

fun mapSignUpError(e: Exception): String =
    when (e) {
        is FirebaseAuthUserCollisionException ->
            "Este e-mail já está em uso."

        is FirebaseAuthWeakPasswordException ->
            "A senha deve ter no mínimo 6 caracteres."

        is FirebaseAuthInvalidCredentialsException ->
            "E-mail inválido."

        else ->
            "Erro ao criar conta. Tente novamente."
    }



