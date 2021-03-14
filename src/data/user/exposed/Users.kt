package data.user.exposed

import org.jetbrains.exposed.sql.Table

object Users : Table("users") {
    val id = integer("id").primaryKey().autoIncrement()
    val name = varchar("name", 100)
    val login = varchar("login", 100)
    val password = varchar("password", 100)
}