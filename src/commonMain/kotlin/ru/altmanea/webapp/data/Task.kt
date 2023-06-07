package ru.altmanea.webapp.data

import kotlinx.serialization.Serializable

@Serializable
class Task(
    var type_of_work: String,
    var subject: String,
) {
    override fun toString() = "$subject - $type_of_work"
}
