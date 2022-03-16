package features.worker

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import util.respondError

fun Route.worker(
        getWorkerInteractor: GetWorkerInteractor = GetWorkerInteractor()
) = route("/test"){
        get("/worker") {
                val workers = getWorkerInteractor.execute()
                if(workers.isNullOrEmpty()){
                        call.respondError("TRMON", HttpStatusCode.NoContent)
                }
                else{
                        call.respond(HttpStatusCode.OK,workers)
                }
        }
}