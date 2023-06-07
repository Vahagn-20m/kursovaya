import component.CFindDiscipline
import component.General
import component.CFindStudent
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter
import react.router.dom.Link
import ru.altmanea.webapp.config.Config
import tanstack.query.core.QueryClient
import tanstack.react.query.QueryClientProvider
import tanstack.react.query.devtools.ReactQueryDevtools
import web.dom.document

fun main() {
    val container = document.getElementById("root")!!
    createRoot(container).render(app.create())
}

val app = FC<Props>("App") {
    HashRouter {
        QueryClientProvider {
            client = QueryClient()
            ul {
                li {
                    Link {
                        +"Общие данные"
                        to = Config.readPath
                    }
                }
                li {
                    Link {
                        +"По диспиплине"
                        to = Config.readPath + "Discipline"
                    }
                }
                li {
                    Link {
                        +"По студенту"
                        to = Config.readPath + "ByName"
                    }
                }
            }

            Routes {
                Route {
                    path = Config.readPath
                    element = General.create()
                }
                Route {
                    path = Config.readPath + "Discipline"
                    element = CFindDiscipline.create()
                }
                Route {
                    path = Config.readPath + "ByName"
                    element = CFindStudent.create()
                }
            }
            ReactQueryDevtools { }
        }
    }
}