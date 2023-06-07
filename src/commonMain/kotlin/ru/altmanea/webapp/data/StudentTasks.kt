package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class StudentTasks(
    var student: Student,
    var grades: List<Grades>
)
