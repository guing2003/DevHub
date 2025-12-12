package com.delecrode.devhub.data.mapper

import com.delecrode.devhub.data.model.UserForFirebaseDto
import com.delecrode.devhub.data.model.UserForGitDto
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit

fun UserForGitDto.toUserDomain(): UserForGit {
    return UserForGit(
        login = login,
        avatar_url = avatar_url,
        url = url ,
        name = name,
        bio = bio,
        repos_url = repos_url
    )
}


fun UserForFirebaseDto.toUserDomain(): UserForFirebase{
    return UserForFirebase(
        fullName = fullName,
        username = username,
        email = email
    )
}
