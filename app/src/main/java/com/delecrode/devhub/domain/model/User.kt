package com.delecrode.devhub.domain.model


//User for GitHub
data class UserForGit(
    val login: String?,
    val avatar_url : String?,
    val url : String?,
    val name: String?,
    val bio: String?,
    val repos_url : String?
)


//User For Firebase
data class RegisterUser(
    val fullName: String = "",
    val username: String = "",
    val email: String = ""
)

data class UserForFirebase(
    val fullName: String = "",
    val username: String = "",
    val email: String = ""
)

data class UserAuth(
    val uid: String,
    val email: String?
)


