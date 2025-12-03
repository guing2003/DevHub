package com.delecrode.devhub.data.mapper

import com.delecrode.devhub.data.model.UserDto
import com.delecrode.devhub.domain.model.User

fun UserDto.toUserDomain(): User {
    return User(
        login = login,
        avatar_url = avatar_url,
        url = url ,
        name = name,
        bio = bio,
        repos_url = repos_url
    )
}
