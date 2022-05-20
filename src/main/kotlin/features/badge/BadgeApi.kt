package features.badge

import features.badge.entity.BadgeRequest
import features.badge.interactor.EndBadgeInteractor
import features.badge.interactor.GetBadgeInteractor
import features.badge.interactor.InsertBadgeInteractor
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

const val PARAM_UUID = "uuid"
const val PARAM_DATE ="date"

fun Route.badge(
     insertBadgeInteractor: InsertBadgeInteractor = InsertBadgeInteractor(),
     getBadgeInteractor: GetBadgeInteractor = GetBadgeInteractor(),
     endBadgeInteractor: EndBadgeInteractor = EndBadgeInteractor()
){
    route("/test"){
         post("/badge") {
              val badgeRequest = call.receive<BadgeRequest>()
              val badge = getBadgeInteractor.execute(badgeRequest)
              if(badge != null){
                   val count =  endBadgeInteractor.execute(badge,badgeRequest)
                    if(count > 0)
                         call.respond(HttpStatusCode.OK,badge.copy(end = badgeRequest.time))
                   else
                        call.respond(HttpStatusCode.InternalServerError,"Cant update badge")
              }
              else{
                   val result = insertBadgeInteractor.execute(badgeRequest)
                   if(result != null){

                        call.respond(HttpStatusCode.OK,result)
                   }
                   else{
                        call.respond(HttpStatusCode.InternalServerError)
                   }
              }

         }
         get("/badge"){
               val uid = call.request.queryParameters[PARAM_UUID]
              val date = call.request.queryParameters[PARAM_DATE]
               if(uid != null && date != null){
                    val badge = getBadgeInteractor.execute(BadgeRequest(date,uid))
                    if(badge != null){
                         call.respond(HttpStatusCode.OK,badge)
                    }
                    else{
                         call.respond(HttpStatusCode.NotFound,)
                    }
               }
              else{
                   call.respond(HttpStatusCode.BadRequest)
               }
         }
    }
}