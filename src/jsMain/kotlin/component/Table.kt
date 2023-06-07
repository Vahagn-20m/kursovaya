package component

import csstype.Color
import emotion.react.css
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.StudentTasks
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

external interface TableProps : Props {
    var items: List<StudentTasks>
}

val CTable = FC<TableProps>("General") { props ->
    table {
        tr {
            td {
                +"Имя студента"
            }
            td {
                +"Группа"
            }
            props.items[0].grades.forEach { grades ->
                ReactHTML.td {
                    +"${grades.task}"
                }
            }
        }
        props.items.forEach { studentTasks ->
            tr {
                td { +studentTasks.student.name_student }
                td { +studentTasks.student.group }
                studentTasks.grades.forEach { grades ->
                    td {
                        val maxGrade = grades.grades?.max()
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