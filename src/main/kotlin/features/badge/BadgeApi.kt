package features.badge

import features.badge.entity.BadgeRequest
import features.badge.interactor.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import util.*

const val PARAM_UUID = "uuid"
const val PARAM_DATE ="date"

fun Route.badge(
     insertBadgeInteractor: InsertBadgeInteractor = InsertBadgeInteractor(),
     getBadgeInteractor: GetBadgeInteractor = GetBadgeInteractor(),
     endBadgeInteractor: EndBadgeInteractor = EndBadgeInteractor(),
     getMonthBadgeInteractor: GetMonthBadgeInteractor = GetMonthBadgeInteractor(),
     modifyBadgeHoursInteractor: ModifyBadgeHoursInteractor = ModifyBadgeHoursInteractor(),
      insertBadgeTimeInteractor: InsertBadgeTimeInteractor = InsertBadgeTimeInteractor()
){
    route(ROUTE_BADGE){
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

         post(PATH_ENTER){
              val badgeRequest = call.receive<BadgeRequest>()
              val badge = getBadgeInteractor.execute(badgeRequest)
              if(badge != null){
                    call.respond(RESPONSE_CODE_BADGE_ENTER_MULTIPLE)
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


         // CONFIRM ENTER
         post(PATH_ENTER_CONFIRM){
              val badgeRequest = call.receive<BadgeRequest>()

                   val result = insertBadgeInteractor.execute(badgeRequest)
                   if(result != null){
                        call.respond(HttpStatusCode.OK,result)
                   }
                   else{
                        call.respond(HttpStatusCode.InternalServerError)
                   }
         }


         post(PATH_EXIT){
              val badgeRequest = call.receive<BadgeRequest>()

         }





         post("/badge_hours") {
              val badgeRequest = call.receive<BadgeRequest>()
              val badge = getBadgeInteractor.execute(badgeRequest)
              if(badge != null){
                   val count =  modifyBadgeHoursInteractor.execute(badge, badgeRequest)
                   if(count > 0)
                        call.respond(HttpStatusCode.OK,badge.copy(workedMinutes = badgeRequest.hours))
                   else
                        call.respond(HttpStatusCode.InternalServerError,"Cant update badge")
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

         get("/badge_month"){
              val uid = call.request.queryParameters[PARAM_UUID]
              val date = call.request.queryParameters[PARAM_DATE]
              if(uid != null && date != null){
                   val badges = getMonthBadgeInteractor.execute(BadgeRequest(date,uid))
                   call.respond(HttpStatusCode.OK,badges)
              }
              else{
                   call.respond(HttpStatusCode.BadRequest)
              }
         }



    }
}

