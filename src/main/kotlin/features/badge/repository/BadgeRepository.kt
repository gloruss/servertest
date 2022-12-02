package features.badge.repository

import features.badge.database.BadgeDao
import features.badge.database.BadgeTime
import features.badge.entity.Badge
import features.badge.entity.BadgeRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.*
import util.dbQuery
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.ofPattern
import java.time.temporal.ChronoUnit
import java.util.*


class BadgeRepository {

    val formatter = ofPattern("yyyy-MM-dd HH:mm")


    suspend fun insertBadge(badgeRequest: BadgeRequest) =
        dbQuery {
                BadgeDao.insert {
                    it[start] = LocalDateTime.parse(badgeRequest.time,formatter)
                    it[worker_uuid] = UUID.fromString(badgeRequest.worker_uuid)
                }.resultedValues?.map {
                    toBadge(it)
                }?.singleOrNull()
            }

    suspend fun insertBadgeTime(badgeRequest: BadgeRequest) =
        dbQuery {
            BadgeTime.insert {
                it[time] = LocalDateTime.parse(badgeRequest.time,formatter)
                it[worker_uuid] = UUID.fromString(badgeRequest.worker_uuid)
                it[isEnter] = badgeRequest.enter
            }
        }


    suspend fun modifyBadge(badge: Badge, badgeRequest: BadgeRequest) = dbQuery {

        BadgeDao.update ({
            BadgeDao.id eq(badge.id)
        }
        ) {
            val startDate = LocalDateTime.parse(badge.start,formatter)

            val endTime = LocalDateTime.parse(badgeRequest.time,formatter)
            it[end] = endTime
             //Duration.between(startDate,endTime).toMillis()
            val duration =ChronoUnit.MINUTES.between(startDate,endTime)
            it[hours] = duration.toInt()

        }
    }


    suspend fun modifyBadgeHours(badge: Badge, badgeRequest: BadgeRequest) = dbQuery {
        BadgeDao.update ({
            BadgeDao.id eq(badge.id)
        }) {
            it[hours] = badgeRequest.hours
        }
    }




     suspend fun getBadgeforWorker(workerUUID: UUID, date : String) : Badge? = dbQuery {
         BadgeDao.select {
             val formattedDate = LocalDateTime.parse(date, formatter)

             (BadgeDao.worker_uuid eq(workerUUID)) and (BadgeDao.start.date() eq(formattedDate.toLocalDate()) )}
             .map { toBadge(it) }.firstOrNull()
     }

    suspend fun getBadgeInMonth(workerUUID: UUID, date: String): List<Badge> = dbQuery {
        BadgeDao.select {
            val formattedDate = LocalDateTime.parse(date, formatter)
            (BadgeDao.worker_uuid eq(workerUUID)) and (BadgeDao.start.month() eq(formattedDate.month.value) )}
            .map { toBadge(it) }
        }





    private fun toBadge(row: ResultRow) : Badge = Badge(
        id = row[BadgeDao.id].value,
        start = row[BadgeDao.start].format(formatter),
        end = row.getOrNull(BadgeDao.end)?.format(formatter),
        worker_id = row.getOrNull(BadgeDao.workerId),
        worker_uuid = row[BadgeDao.worker_uuid].toString(),
        workedMinutes = row.getOrNull(BadgeDao.hours),
        type = row.getOrNull(BadgeDao.type) ?:""
    )
}