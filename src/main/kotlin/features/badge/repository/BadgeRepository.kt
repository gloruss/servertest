package features.badge.repository

import features.badge.database.BadgeDao
import features.badge.entity.Badge
import features.badge.entity.BadgeRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.Day
import org.jetbrains.exposed.sql.javatime.dateParam
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.day
import util.dbQuery
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ofPattern
import java.util.*


class BadgeRepository {

    val simpletimeFormat = SimpleDateFormat("HH:mm")
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    suspend fun insertBadge(badgeRequest: BadgeRequest) =
        dbQuery {
                BadgeDao.insert {
                    it[start] = datetime(badgeRequest.time)
                    it[worker_uuid] = UUID.fromString(badgeRequest.worker_uuid)
                }.resultedValues?.map {
                    toBadge(it)
                }?.singleOrNull()
            }




    suspend fun modifyBadge(badge: BadgeRequest) = dbQuery {
        BadgeDao.update {
            it[end] = datetime(badge.time)
        }
    }



     suspend fun getBadgeforWorker(workerUUID: UUID, date : String) : Badge? = dbQuery {
         BadgeDao.select {
             val formatter = ofPattern("yyyy-MM-dd HH:mm")
             val formattedDate = LocalDate.parse(date, formatter)

             (BadgeDao.worker_uuid eq(workerUUID)) and ((BadgeDao.start) eq(dateParam(formattedDate)) ) }
             .map { toBadge(it) }.firstOrNull()
     }





    private fun toBadge(row: ResultRow) : Badge = Badge(
        id = row[BadgeDao.id].value,
        start = row[BadgeDao.start].toString(),
        end = row.getOrNull(BadgeDao.end).toString(),
        worker_id = row.getOrNull(BadgeDao.workerId),
        worker_uuid = row[BadgeDao.worker_uuid].toString(),
        hours = row.getOrNull(BadgeDao.hours) ?: 0,
        type = row.getOrNull(BadgeDao.type) ?:""
    )
}