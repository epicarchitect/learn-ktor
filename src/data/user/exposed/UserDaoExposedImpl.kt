package data.user.exposed

import ktor.learn.domain.user.User
import domain.user.UserDao
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserDaoExposedImpl(private val database: Database) : UserDao {

    override fun insert(user: User) {
        transaction(database) {
            Users.insert {
                it[name] = user.name
                it[login] = user.login
                it[password] = user.password
            }
        }
    }

    override fun update(user: User) {
        transaction(database) {
            Users.update(where = { Users.id eq user.id }) {
                it[name] = user.name
                it[login] = user.login
                it[password] = user.password
            }
        }
    }

    override fun deleteById(id: Int) {
        transaction(database) {
            Users.deleteWhere { Users.id eq id }
        }
    }

    override fun getById(id: Int) = transaction(database) {
        Users.select { Users.id eq id }.singleOrNull()?.toUser()
    }

    override fun getAll() = transaction(database) {
        Users.selectAll().map { it.toUser() }
    }

    private fun ResultRow.toUser() = User(
        get(Users.id),
        get(Users.name),
        get(Users.login),
        get(Users.password)
    )
}