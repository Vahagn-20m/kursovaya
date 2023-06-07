package component

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.p
import react.useState
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.StudentTasks
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText
import web.html.InputType

val CFindStudent = FC<Props>("TableWithStudent") {
    var input by useState("")

    div {
        input {
            placeholder = "Введите имя студента"
            type = InputType.text
            onChange = { input = it.target.value }
        }
    }

    val selectQueryKey = arrayOf("StudentSelect", input).unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = selectQueryKey,
        queryFn = {
            fetchText(
                "${Config.readPath}ByName/${input}"
            )
        }
    )

    val items: List<StudentTasks> =
        try {
            Json.decodeFromString(query.data ?: "")
        } catch (e: Throwable) {
            emptyList()
        }
    if (items.isNotEmpty()) {
        CTable {
            this.items = items
        }
    }
}