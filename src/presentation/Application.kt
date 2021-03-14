package ktor.learn

import data.user.exposed.UserDaoExposedImpl
import domain.user.UserController
import domain.user.UserDao
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

const val DATABASE_NAME = "my_test_database"
val userController = UserController(UserDao())

fun UserDao(): UserDao {
    return UserDaoExposedImpl(
        Database.connect(
            url = "jdbc:mariadb://localhost:3306/$DATABASE_NAME",
            driver = "org.mariadb.jdbc.Driver",
            user = "root",
            password = "root"
        )
    )
    //return UserDaoHardImpl(
    //    DriverManager.getConnection("jdbc:mariadb://localhost:3306/$DATABASE_NAME", "root", "root")
    //)
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }

        userRouting()
    }.start(wait = true)
}

fun Application.userRouting() = routing {
    post("user/register") {
        userController.register(call.receive())
    }

    put("user") {
        userController.update(call.receive())
    }

    delete("user/{id}") {
        userController.deleteById(call.parameters["id"]!!.toInt())
    }

    get("user/{id}") {
        call.respondText(userController.getById(call.parameters["id"]!!.toInt()).toString())
    }

    get("user/all") {
        call.respond(userController.getAll())
    }
}
