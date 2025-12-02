package com.delecrode.devhub.data.mapper

import com.delecrode.devhub.data.model.UserDto
import com.delecrode.devhub.domain.model.User

fun UserDto.toUserDomain(): User {
    return User(
        login = login ?: "Sem Nome de Usuario",
        avatar_url = avatar_url ?: "",
        url = url ?: "Sem Url de perfil",
        name = name?: "Sem nome",
        bio = bio ?: "Sem Biografia",
        repos_url = repos_url ?: ""
    )
}
