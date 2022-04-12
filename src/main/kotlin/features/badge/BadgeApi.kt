package features.badge

import features.badge.entity.Badge
import features.badge.entity.BadgeRequest
import features.badge.interactor.InsertBadgeInteractor
import features.worker.interactor.InsertWorkerInteractor
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.get

fun Route.badge(
     insertBadgeInteractor: InsertBadgeInteractor = InsertBadgeInteractor()
){
    route("/test"){
         post("/badge") {
              val badge = call.receive<BadgeRequest>()
              val result = insertBadgeInteractor.execute(badge)
              if(result != null){

                   call.respond(HttpStatusCode.OK,result)
              }
              else{
                   call.respond(HttpStatusCode.InternalServerError)
              }
         }
         get("/badge/{id}"){

         }
    }
}