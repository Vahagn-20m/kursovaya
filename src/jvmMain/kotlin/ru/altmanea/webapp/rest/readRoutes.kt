package ru.altmanea.webapp.rest

import com.mongodb.client.MongoCollection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.litote.kmongo.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.*
import ru.altmanea.webapp.mongo.studentTasksDB

fun disciplineGrades(name: String, type: String, studentsWithGrades: MongoCollection<StudentTasks>): List<Discipline>? {
    val tmp = studentsWithGrades.aggregate<Discipline>(
        unwind("\$grades"),
        match(
            and(
                StudentTasks::grades / Grades::task / Task::subject eq name,
                StudentTasks::grades / Grades::task / Task::type_of_work eq type
            )
        )
    ).toList()
    return tmp.ifEmpty { null }
}

fun Route.readRoutes() {
    route(Config.readPath) {
        get {
            val list: List<StudentTasks> = studentTasksDB.find().toList()
            if (list.isEmpty()) {
                call.respondText(
                    "База данных пуста",
                    status = HttpStatusCode.NotFound
                )
            } else {
                call.respond(list)
            }
        }
        get("Discipline/{name}") {
            val encode = call.parameters["name"] ?:
            return@get call.respondText(
                "Ошибка при передаче объекта",
                status = HttpStatusCode.NotFound
            )
            val pair = Json.decodeFromString<Pair<String, String>>(encode)
            val subject = pair.first
            val type = pair.second
            val list = disciplineGrades(subject, type, studentTasksDB)
            if (list != null) {
                call.respond(list)
            } else {
                call.respondText(
                    "Информация о студенте не найдена",
                    status = HttpStatusCode.NotFound
                )
            }
        }
        get("ByName/{name}") {
            val name = call.parameters["name"] ?:
            return@get call.respondText(
                "Ошибка при передаче имени",
                status = HttpStatusCode.NotFound
            )
            val list: List<StudentTasks> =
                studentTasksDB.find(StudentTasks::student / Student::name_student eq name).toList()
            if (list.isEmpty()) {
                call.respondText(
                    "Информация о студенте не найдена",
                    status = HttpStatusCode.NotFound
                )
            } else {
                call.respond(list)
            }
        }
    }
}