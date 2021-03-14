package domain.user

import ktor.learn.domain.user.User

interface UserDao {

    fun insert(user: User)

    fun update(user: User)

    fun deleteById(id: Int)

    fun getById(id: Int): User?

    fun getAll(): List<User>

}