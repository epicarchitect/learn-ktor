package domain.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ktor.learn.domain.user.User

class UserController(private val dao: UserDao) {

    suspend fun register(user: User) = withContext(Dispatchers.IO) {
        dao.insert(user)
    }

    suspend fun update(user: User) = withContext(Dispatchers.IO) {
        dao.update(user)
    }

    suspend fun deleteById(id: Int) = withContext(Dispatchers.IO) {
        dao.deleteById(id)
    }

    suspend fun logout(id: Int) = withContext(Dispatchers.IO) {
        // TODO: implement
    }

    suspend fun getAll() = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun getById(id: Int) = withContext(Dispatchers.IO) {
        dao.getById(id)
    }
}