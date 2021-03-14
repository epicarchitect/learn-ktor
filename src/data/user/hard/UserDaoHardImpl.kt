package data.user.hard

import ktor.learn.domain.user.User
import domain.user.UserDao
import java.sql.Connection
import java.sql.ResultSet

class UserDaoHardImpl(private val connection: Connection) : UserDao {

    init {

    }

    override fun insert(user: User) {
        with(connection.prepareStatement("INSERT INTO users (name, login, password) VALUES (?, ?, ?)")) {
            setString(1, user.name)
            setString(2, user.login)
            setString(3, user.password)
            execute()
        }
    }

    override fun update(user: User) {
        connection.prepareStatement("UPDATE users SET name = '${user.name}', login = '${user.login}', password = '${user.password}' WHERE id = ${user.id}")
            .execute()
    }

    override fun deleteById(id: Int) {
        connection.prepareStatement("DELETE FROM users WHERE id = $id").execute()
    }

    override fun getById(id: Int): User? {
        val result = connection.createStatement().executeQuery("SELECT * FROM users WHERE id = $id")
        return if (result.next()) {
            result.toUser()
        } else {
            null
        }
    }

    override fun getAll(): List<User> {
        val result = connection.createStatement().executeQuery("SELECT * FROM users")
        val list = ArrayList<User>()

        while (result.next()) {
            list.add(result.toUser())
        }

        return list
    }

    private fun ResultSet.toUser() = User(
        getInt(findColumn("id")),
        getString(findColumn("name")),
        getString(findColumn("login")),
        getString(findColumn("password"))
    )
}