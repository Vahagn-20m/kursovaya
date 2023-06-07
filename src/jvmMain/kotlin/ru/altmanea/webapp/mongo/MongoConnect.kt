package ru.altmanea.webapp.mongo


import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection
import ru.altmanea.webapp.data.StudentTasks

val client = KMongo.createClient("mongodb://127.0.0.1:27017")
val database: MongoDatabase = client.getDatabase("test")
val studentTasksDB = database.getCollection<StudentTasks>("student_Tasks")