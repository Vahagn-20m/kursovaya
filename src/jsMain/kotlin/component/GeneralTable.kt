package component

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.StudentTasks
import tanstack.query.core.QueryKey
import tanstack.react.query.useQuery
import tools.fetchText

val General = FC<Props>("General") {
    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = arrayOf("GeneralList").unsafeCast<QueryKey>(),
        queryFn = {
            fetchText(Config.readPath)
        }
    )

    if (query.isLoading) ReactHTML.div { +"Loading .." }
    else if (query.isError) ReactHTML.div { +"Error!" }
    else {
        val items =
            Json.decodeFromString<List<StudentTasks>>(query.data ?: "")
        CTable {
            this.items = items
        }
    }
}