package com.delecrode.devhub.utils


fun mapHttpError(code: Int): String =
    when (code) {
        404 -> "Repositório não encontrado"
        403 -> "Limite da API atingido"
        in 500..599 -> "Servidor indisponível"
        else -> "Erro ao buscar dados"
    }

