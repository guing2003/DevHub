package com.delecrode.devhub.data.mapper

import com.delecrode.devhub.data.model.dto.UserAuthDto
import com.delecrode.devhub.data.model.dto.UserForFirebaseDto
import com.delecrode.devhub.data.model.dto.UserForGitDto
import com.delecrode.devhub.domain.model.UserAuth
import com.delecrode.devhub.domain.model.UserForFirebase
import com.delecrode.devhub.domain.model.UserForGit
import com.google.firebase.auth.FirebaseUser

fun UserForGitDto.toUserGitDomain(): UserForGit {
    return UserForGit(
        login = login,
        avatar_url = avatar_url,
        url = url ,
        name = name,
        bio = bio,
        repos_url = repos_url
    )
}


fun UserForFirebaseDto.toUserFirebaseDomain(): UserForFirebase{
    return UserForFirebase(
        fullName = fullName,
        username = username,
        email = email
    )
}

fun FirebaseUser.toUserAuthDomain(): UserAuth =
    UserAuth(
        uid = uid,
        email = email
    )


