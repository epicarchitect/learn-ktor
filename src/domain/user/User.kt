package ktor.learn.domain.user

data class User(
    val id: Int,
    val name: String,
    val login: String,
    val password: String
)