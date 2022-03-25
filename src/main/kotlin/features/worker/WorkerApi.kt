package features.worker

import features.worker.entity.Worker
import features.worker.interactor.InsertWorkerInteractor
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import util.respondError

fun Route.worker(
        getWorkerInteractor: GetWorkerInteractor = GetWorkerInteractor(),
        insertWorkerInteractor: InsertWorkerInteractor = InsertWorkerInteractor()
) = route("/test"){
        get("/worker") {
                val workers = getWorkerInteractor.execute()
                if(workers.isNullOrEmpty()){
                        call.respondError("TRMON", HttpStatusCode.InternalServerError)
                }
                else{
                        call.respond(HttpStatusCode.OK,workers)
                }
        }
        post("/worker") {
                val worker = call.receive<Worker>()
                val result = insertWorkerInteractor.execute(worker)
                if(result != null){
                        call.respond(HttpStatusCode.OK,result)
                }
                else{
                        call.respond(HttpStatusCode.InternalServerError)
                }

        }
}