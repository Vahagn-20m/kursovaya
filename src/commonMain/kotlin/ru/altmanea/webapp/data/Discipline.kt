package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class Discipline(
    var student: Student,
    val grades: Grades
)