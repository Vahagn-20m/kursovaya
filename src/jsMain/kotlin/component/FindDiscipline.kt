package component

import csstype.Color
import csstype.px
import emotion.react.css
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import react.useState
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.Discipline
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import web.html.InputType

val CFindDiscipline = FC<Props>("CFindDiscipline") {
    var subject by useState("")
    var typeSubject by useState("")

    div {
        input {
            placeholder = "Введите название дисциплины"
            type = InputType.text
            onChange = { subject = it.target.value }
            size = 35
        }
        input {
            placeholder = "Введите тип занятия"
            type = InputType.text
            onChange = { typeSubject = it.target.value }
        }
    }

    val selectQueryKey = arrayOf("StudentSelect", subject, typeSubject).unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = selectQueryKey,
        queryFn = {
            fetchText(
                "${Config.readPath}Discipline/${Json.encodeToString(Pair(subject,typeSubject))}"
            )
        }
    )

    val items: List<Discipline> =
        try {
            Json.decodeFromString(query.data ?: "")
        } catch (e: Throwable) {
            emptyList()
        }
    if (items.isNotEmpty()) {
        table {
            tr {
                td {
                    +"Имя студента"
                }
                td {
                    +"Группа"
                }
                td {
                    +"${items[0].grades.task}"
                }
            }
            items.forEach { discipline ->
                tr {
                    td { +discipline.student.name_student }
                    td { +discipline.student.group }
                    td {
                        val maxGrade = discipline.grades.grades?.max()
                        if (maxGrade == null) {
                            css {
                                background = Color("Green")
                            }
                            +"Долг"
                        } else {
                            +maxGrade.toString()
                        }
                    }
                }
            }
        }
    }
}