package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class Student(
    var name_student: String,
    var group: String
)