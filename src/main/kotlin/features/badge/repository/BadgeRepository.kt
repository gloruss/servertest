package features.badge.repository

import features.badge.database.BadgeDao
import features.badge.entity.Badge
import features.badge.entity.BadgeRequest
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter

import util.dbQuery
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter.*
import java.util.*



class BadgeRepository {

    val simpletimeFormat = SimpleDateFormat("HH:mm")
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    suspend fun insertBadge(badge: BadgeRequest) =
        dbQuery {
            val savedBadge = getBadgeforWorker(UUID.fromString(badge.worker_uuid), badge.date)
            if(savedBadge != null){
                BadgeDao.update {
                    it[end] = DateTime.parse(badge.time)

                }
                savedBadge.copy(end = badge.time)
            }
            else{
                BadgeDao.insert {
                    it[start] = DateTime.parse(badge.time)
                    it[date] = DateTime.parse(badge.date)
                    it[worker_uuid] = UUID.fromString(badge.worker_uuid)
                }.resultedValues?.map {
                    toBadge(it)
                }?.singleOrNull()
            }

        }



     fun getBadgeforWorker(workerUUID: UUID, date : String) : Badge? =
        BadgeDao.select { (BadgeDao.worker_uuid eq(workerUUID)) and ((BadgeDao.date) eq(DateTime.parse(date))  ) }
            .map { toBadge(it) }.firstOrNull()



    private fun toBadge(row: ResultRow) : Badge = Badge(
        id = row[BadgeDao.id],
        start = row[BadgeDao.start].toString(),
        end = row.getOrNull(BadgeDao.end).toString(),
        date = row[BadgeDao.date].toString(),
        worker_id = row.getOrNull(BadgeDao.workerId),
        worker_uuid = row[BadgeDao.worker_uuid].toString(),
        hours = row.getOrNull(BadgeDao.hours) ?: 0,
        type = row.getOrNull(BadgeDao.type) ?:""
    )
}