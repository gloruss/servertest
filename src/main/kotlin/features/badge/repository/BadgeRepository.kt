package features.badge.repository

import features.badge.database.BadgeDao
import features.badge.entity.Badge
import features.badge.entity.BadgeRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.*
import org.jetbrains.exposed.sql.javatime.Date

import util.dbQuery
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.*
import java.util.*


class BadgeRepository {

    val simpletimeFormat = SimpleDateFormat("HH:mm")
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    suspend fun insertBadge(badge: BadgeRequest) =
        dbQuery {
                BadgeDao.insert {
                    it[start] = datetime(badge.time)
                    it[worker_uuid] = UUID.fromString(badge.worker_uuid)
                }.resultedValues?.map {
                    toBadge(it)
                }?.singleOrNull()
            }




    suspend fun modifyBadge(badge: BadgeRequest) = dbQuery {
        BadgeDao.update {
            it[end] = datetime(badge.time)
        }
    }



     suspend fun getBadgeforWorker(workerUUID: UUID, date : String) : Badge? =
        BadgeDao.select {
           val  format = ofPattern("yyyy-MM-dd HH:mm:ss.SSSX")
            (BadgeDao.worker_uuid eq(workerUUID)) and ((BadgeDao.date.day()) eq(dateParam(LocalDate.parse(date,format)) )) }
            .map { toBadge(it) }.firstOrNull()




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