package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class Grades(
    var task: Task,
    var grades: List<Int>?
)
